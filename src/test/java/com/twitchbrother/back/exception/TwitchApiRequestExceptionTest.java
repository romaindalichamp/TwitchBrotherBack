package com.twitchbrother.back.exception;


import com.fasterxml.jackson.core.JsonProcessingException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TwitchApiRequestExceptionTest{

  @Test
  public void throwsException() {
    try {
      throw new TwitchApiRequestException("message", new JsonProcessingException(""){});
    } catch (RuntimeException e) {
      Assertions.assertThat(e)
          .isInstanceOf(TwitchApiRequestException.class)
          .hasMessage("message");
    }
  }
}
