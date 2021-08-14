package com.twitchbrother.back.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.twitchbrother.back.TwitchConfigurationProperties;
import com.twitchbrother.back.model.TwitchStreamsModel;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

/**
 * https://github.com/AsyncHttpClient/async-http-client
 */
@Component
public class TwitchAPIClient {

  RestTemplate restTemplate = new RestTemplate();
  private final TwitchConfigurationProperties twitchConfigurationProperties;
  private static final Logger LOG = LoggerFactory.getLogger(TwitchAPIClient.class);
  private final String authorizationKey;
  private final String authorizationValue;
  private final String clientIdKey;
  private final String clientIdValue;
  private final JsonMapper jsonMapper = new JsonMapper();
  private final String twitchGameStreamUrl;
  private final String gameListRequestParameters;
  private final String afterParameter;

  public TwitchAPIClient(TwitchConfigurationProperties twitchConfigurationProperties) {

    this.twitchConfigurationProperties = twitchConfigurationProperties;
    this.authorizationKey = twitchConfigurationProperties.getApi().getAuthorization().getKey();
    this.authorizationValue = twitchConfigurationProperties.getApi().getAuthorization().getValue();
    this.clientIdKey = twitchConfigurationProperties.getApi().getClientId().getKey();
    this.clientIdValue = twitchConfigurationProperties.getApi().getClientId().getValue();
    twitchGameStreamUrl = this.twitchConfigurationProperties.getApi().getHelix().getStreams()
        .getUrl();
    gameListRequestParameters = this.twitchConfigurationProperties.getApi().getHelix().getStreams()
        .createGameListRequestParameters();
    afterParameter = this.twitchConfigurationProperties.getApi().getHelix().getStreams()
        .getAfterParameter();

    //TODO: Twitch Api Connection with Authorization Token Refresh
    //TODO: Proper Runtime Exceptions ==> AOP for reconnection ?
    //TODO: Redis database connection to save specific datas https://dashboard.heroku.com/provision-addon?addonServiceId=5bbf672c-07f6-49c2-9c16-f1dcb96784db&planId=67756275-86b8-4edc-80c5-d543f9df7d44
    //TODO: Vault to save secrets https://dashboard.heroku.com/new?button-url=https%3A%2F%2Felements.heroku.com%2F&template=https%3A%2F%2Fgithub.com%2Fpallavkothari%2Fvault
    //TODO: WEB SOCKET
    //TODO: ANGULAR
    //TODO: TUs
    // sorting
  }

  public TwitchStreamsModel pollHelixStream(final String cursor) {
    try {
      URL url = new URL(new StringBuilder()
          .append(twitchGameStreamUrl)
          .append(gameListRequestParameters)
          .append((Objects.nonNull(cursor)) ? afterParameter : "")
          .append((Objects.nonNull(cursor)) ? cursor : "").toString());

      HttpHeaders headers = new HttpHeaders();
      headers.add(authorizationKey, authorizationValue);
      headers.add(clientIdKey, clientIdValue);
      HttpEntity request = new HttpEntity(headers);
      ResponseEntity<String> response =
          restTemplate.exchange(
              String.valueOf(url),
              HttpMethod.GET,
              request, String.class);

      // Receive the Response value in String and serialize it with JsonMapper
      //  helps to save more than 1 second for a group of requests / loop
      return jsonMapper.readValue(response.getBody(),
          TwitchStreamsModel.class);
    } catch (JsonProcessingException | MalformedURLException e) {
      LOG.info("Exception {}", e);
      throw new RuntimeException(e);
    }
  }
}
