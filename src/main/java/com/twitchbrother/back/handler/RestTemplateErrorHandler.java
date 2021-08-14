package com.twitchbrother.back.handler;

import java.io.IOException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.ResponseErrorHandler;

/**
 * Using AOP to handle Twitch API connections errors (mainly authentication)
 */
@Component
public class RestTemplateErrorHandler implements ResponseErrorHandler {

  @Override
  public boolean hasError(ClientHttpResponse httpResponse) throws IOException {
    return (
        httpResponse.getStatusCode().series() == Series.CLIENT_ERROR
            || httpResponse.getStatusCode().series() == Series.SERVER_ERROR);
  }

  @Override
  public void handleError(ClientHttpResponse httpResponse)
      throws IOException {

    if (httpResponse.getStatusCode().series() == HttpStatus.Series.SERVER_ERROR) {
      throw new HttpServerErrorException(httpResponse.getStatusCode(), "SERVER ERROR");
    } else if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
      // bad request for debug
      if (httpResponse.getStatusCode().value() != HttpStatus.UNAUTHORIZED.value()
          && httpResponse.getStatusCode().value() != HttpStatus.BAD_REQUEST.value()) {
        throw new HttpClientErrorException(httpResponse.getStatusCode(), "NOT FOUND");
      }
    }
  }
}
