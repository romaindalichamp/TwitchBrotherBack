package com.twitchbrother.back.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.twitchbrother.back.CustomConfigurationProperties;
import com.twitchbrother.back.exception.TwitchApiAuthenticationException;
import com.twitchbrother.back.exception.TwitchApiRequestException;
import com.twitchbrother.back.handler.RestTemplateErrorHandler;
import com.twitchbrother.back.model.TwitchApiAuthenticationModel;
import com.twitchbrother.back.model.TwitchStreamsModel;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

/**
 * A simple non-Asynchronous HttpClient to execute Requests in order to Poll
 * Twitch API as fast as possible to render the configured Streams Datas
 * The configuration can be modified in
 * <p>- application.properties
 * <p>- Environement Variables
 */
@Component
public class TwitchAPIClient {

  private static final int MAX_ATTEMPS_AUTHENTICATION = 10;

  private final RestTemplateBuilder restTemplateBuilder;
  private final RestTemplateErrorHandler restTemplateErrorHandler;
  private final CustomConfigurationProperties customConfigurationProperties;
  private static final Logger LOG = LoggerFactory.getLogger(TwitchAPIClient.class);
  private final String authorizationKey;
  private final String clientIdKey;
  private final String clientIdValue;
  private final JsonMapper jsonMapper = new JsonMapper();
  private final String twitchGameStreamUrl;
  private final String gameListRequestParameters;
  private final String afterParameter;
  private RestTemplate restTemplate;
  private String authenticationUrl;
  private String clientSecretKey;
  private String clientSecretValue;
  private String grantTypeKey;
  private String grantTypeValue;
  private String scopesKey;
  private String scopesValue;
  private HttpEntity request;
  private String accessToken;
  private String tokenType;
  private String clientidHeaderKey;
  private int authenticationFailureNumber = 0;
  private int waitBeforeNextAuthenticationTry = MAX_ATTEMPS_AUTHENTICATION;

  public TwitchAPIClient(
      RestTemplate restTemplate,
      RestTemplateBuilder restTemplateBuilder,
      RestTemplateErrorHandler restTemplateErrorHandler,
      CustomConfigurationProperties customConfigurationProperties) {
    this.restTemplate = restTemplate;
    this.restTemplateBuilder = restTemplateBuilder;
    this.restTemplateErrorHandler = restTemplateErrorHandler;
    this.customConfigurationProperties = customConfigurationProperties;

    this.twitchGameStreamUrl =
        this.customConfigurationProperties
            .getTwitch().getApi().getHelix().getStreams().getUrl();
    this.gameListRequestParameters =
        this.customConfigurationProperties
            .getTwitch().getApi().getHelix().getStreams().createGameListRequestParameters();
    this.afterParameter =
        this.customConfigurationProperties
            .getTwitch().getApi().getHelix().getStreams().getAfterParameter();
    this.authenticationUrl =
        this.customConfigurationProperties.getTwitch().getApi().getHelix().getAuthentication().getUrl();
    this.clientIdKey =
        this.customConfigurationProperties
            .getTwitch().getApi().getHelix().getAuthentication().getClientid().getKey();
    this.clientIdValue =
        this.customConfigurationProperties
            .getTwitch().getApi().getHelix().getAuthentication().getClientid().getValue();
    this.clientSecretKey =
        this.customConfigurationProperties.getTwitch().getApi()
            .getHelix().getAuthentication().getClientsecret().getKey();
    this.clientSecretValue =
        this.customConfigurationProperties.getTwitch().getApi()
            .getHelix().getAuthentication().getClientsecret().getValue();
    this.grantTypeKey =
        this.customConfigurationProperties
            .getTwitch().getApi().getHelix().getAuthentication().getGranttype().getKey();
    this.grantTypeValue =
        this.customConfigurationProperties
            .getTwitch().getApi().getHelix().getAuthentication().getGranttype().getValue();
    this.scopesKey =
        this.customConfigurationProperties
            .getTwitch().getApi().getHelix().getAuthentication().getScopes().getKey();
    this.scopesValue =
        this.customConfigurationProperties
            .getTwitch().getApi().getHelix().getAuthentication().getScopes().getValue();
    this.authorizationKey =
        this.customConfigurationProperties
            .getTwitch().getApi().getHelix().getAuthentication().getAuthorizationkey();
    this.clientidHeaderKey =
        this.customConfigurationProperties
            .getTwitch().getApi().getHelix().getAuthentication().getClientidHeaderKey();

    this.restTemplate.setErrorHandler(restTemplateErrorHandler);
  }

  /**
   * Poll the Twitch Helix Stream API on a predeterminate Endpoint (project properties)
   *
   * @param cursor if there is a cursor, will include it as a param int the URL
   * @return and object {@link TwitchStreamsModel} containing a maximum of 100 Twitch Streams (with
   * datas)
   */
  public TwitchStreamsModel pollHelixStream(final String cursor) {
    try {
      URI uri = new URI(new StringBuilder()
          .append(twitchGameStreamUrl)
          .append(gameListRequestParameters)
          .append((Objects.nonNull(cursor)) ? afterParameter : "")
          .append((Objects.nonNull(cursor)) ? cursor : "").toString());

      HttpHeaders headers = new HttpHeaders();
      headers.add(authorizationKey, tokenType + " " + accessToken);
      headers.add(clientidHeaderKey, clientIdValue);
      request = new HttpEntity(headers);

      ResponseEntity<String> response =
          restTemplate.exchange(uri.toString(), HttpMethod.GET, request, String.class);

      if (response.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
        if (this.authenticate(response)) {
          return this.pollHelixStream(cursor);
        }
      }
      // Receive the Response value in String and serialize it with JsonMapper
      //  helps to save more than 1 second for each group of requests / loop
      return jsonMapper.readValue(response.getBody(), TwitchStreamsModel.class);

    } catch (JsonProcessingException | URISyntaxException e){
      throw new TwitchApiRequestException(e);
    } catch(InterruptedException e) {
      //restore interrupted state
      Thread.currentThread().interrupt();
      throw new TwitchApiRequestException(e);
    }
  }

  /**
   * Authenticate to the Twitch Api if the authentication fail for (Http Status 401), it will retry
   * in 1.5*previous wainting time
   *
   * @param previousRequest the previous try request informations
   * @return a boolean on true if the accessToken and tokenType are correctly retreived
   * @throws URISyntaxException   if the URI is incorrect
   * @throws InterruptedException thread interruption
   */
  private boolean authenticate(ResponseEntity<String> previousRequest)
      throws URISyntaxException, InterruptedException {
    URI uri = new URI(new StringBuilder()
        .append(authenticationUrl)
        .append(clientIdKey + clientIdValue)
        .append("&" + clientSecretKey + clientSecretValue)
        .append("&" + grantTypeKey + grantTypeValue)
        .append("&" + scopesKey + scopesValue)
        .toString());

    try {
      ResponseEntity<TwitchApiAuthenticationModel> response =
          restTemplate.exchange(uri, HttpMethod.POST, request, TwitchApiAuthenticationModel.class);

      if (!response.getStatusCode().is2xxSuccessful() && Objects.nonNull(response.getBody())) {
        if (authenticationFailureNumber < MAX_ATTEMPS_AUTHENTICATION) {
          authenticationFailureNumber++;
          this.authenticate(previousRequest);
        } else {
          throw new TwitchApiAuthenticationException(previousRequest.getBody(),
              waitBeforeNextAuthenticationTry);
        }
      }else{
        waitBeforeNextAuthenticationTry = MAX_ATTEMPS_AUTHENTICATION;
      }

      accessToken = Objects.requireNonNull(response.getBody()).getAccess_token();
      tokenType = StringUtils.capitalize(
          Objects.requireNonNull(response.getBody()).getToken_type());
    } catch (TwitchApiAuthenticationException e) {
      waitBeforeNextAuthenticationTry = waitBeforeNextAuthenticationTry * 150 / 100;
      Thread.sleep(waitBeforeNextAuthenticationTry * 1000L);
      authenticationFailureNumber = 0;
      this.authenticate(previousRequest);
    }

    return Objects.nonNull(this.accessToken) && Objects.nonNull(this.tokenType);
  }

  public void setRestTemplate(RestTemplate restTemplate){
    this.restTemplate = restTemplate;
  }
}
