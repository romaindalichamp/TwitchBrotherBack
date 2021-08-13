package com.twitchbrother.back.gateway;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.twitchbrother.back.TwitchConfigurationProperties;
import com.twitchbrother.back.model.TwitchStreamsModel;
import org.asynchttpclient.AsyncHttpClient;
import org.asynchttpclient.DefaultAsyncHttpClient;
import org.asynchttpclient.ListenableFuture;
import org.asynchttpclient.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

/**
 * https://github.com/AsyncHttpClient/async-http-client
 */
@Component
public class TwitchClient {

  private final TwitchConfigurationProperties twitchConfigurationProperties;
  private static final Logger LOG = LoggerFactory.getLogger(TwitchClient.class);
  AsyncHttpClient client = new DefaultAsyncHttpClient();
  private final String authorizationKey;
  private final String authorizationValue;
  private final String clientIdKey;
  private final String clientIdValue;
  private final JsonMapper jsonMapper = new JsonMapper();
  private final String twitchGameStreamUrl;
  private final String gameListRequestParameters;
  private final String afterParameter;

  public TwitchClient(TwitchConfigurationProperties twitchConfigurationProperties) {
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
  }

  public TwitchStreamsModel pollHelixStream(final String cursor) {
    try {
      URL url = new URL(new StringBuilder()
          .append(twitchGameStreamUrl)
          .append(gameListRequestParameters)
          .append((Objects.nonNull(cursor)) ? afterParameter : "")
          .append((Objects.nonNull(cursor)) ? cursor : "").toString());

      ListenableFuture<Response> result = client
          .prepareGet(String.valueOf(url))
          .addHeader(authorizationKey, authorizationValue)
          .addHeader(clientIdKey, clientIdValue)
          .execute();

      LOG.trace("Poll Streams to: {}", url);
      return jsonMapper.readValue(result.toCompletableFuture().get().getResponseBody(),
          TwitchStreamsModel.class);
    } catch (InterruptedException e) {
      e.printStackTrace();
      throw new RuntimeException();
    } catch (ExecutionException e) {
      e.printStackTrace();
      throw new RuntimeException();
    } catch (JsonProcessingException e) {
      e.printStackTrace();
      throw new RuntimeException();
    } catch (MalformedURLException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }
}
