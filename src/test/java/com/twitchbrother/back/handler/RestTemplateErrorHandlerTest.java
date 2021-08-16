package com.twitchbrother.back.handler;

import java.io.IOException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatus.Series;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.HttpServerErrorException;

@ExtendWith(SpringExtension.class)
public class RestTemplateErrorHandlerTest {

  private RestTemplateErrorHandler restTemplateErrorHandler = new RestTemplateErrorHandler();

  @Mock
  private ClientHttpResponse clientHttpResponse;

  @Test
  @DisplayName("hasClientError")
  public void hasClientError() throws IOException {
    Mockito.when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
    Assertions.assertThat(this.restTemplateErrorHandler.hasError(clientHttpResponse)).isTrue();
  }

  @Test
  @DisplayName("hasServerError")
  public void hasServerError() throws IOException {
    Mockito.when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
    Assertions.assertThat(this.restTemplateErrorHandler.hasError(clientHttpResponse)).isTrue();
  }

  @Test
  @DisplayName("hasNoError")
  public void hasNoError() throws IOException {
    Mockito.when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.OK);
    Assertions.assertThat(this.restTemplateErrorHandler.hasError(clientHttpResponse)).isFalse();
  }

  @Test
  @DisplayName("hasClientErrorButNoException")
  public void hasClientErrorButNoException() throws IOException {
    Mockito.when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.BAD_REQUEST);
    Assertions.assertThatNoException()
        .isThrownBy(() -> this.restTemplateErrorHandler.handleError(clientHttpResponse));
  }

  @Test
  @DisplayName("hasClientErrorButWithException")
  public void hasClientErrorButWithException() throws IOException {
//    Mockito.when(clientHttpResponse.getStatusCode()).thenReturn(HttpStatus.INTERNAL_SERVER_ERROR);
//    Assertions.assertThatThrownBy(
//        () -> this.restTemplateErrorHandler.handleError(clientHttpResponse)).isExactlyInstanceOf(
//        HttpServerErrorException.class);
  }
}
