package com.twitchbrother.back.exception;


import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TwitchApiAuthenticationExceptionTest {


  @Test
  public void throwsException() {
    try {
      throw new TwitchApiAuthenticationException("message", 1);
    } catch (RuntimeException e) {
      Assertions.assertThat(e)
          .isInstanceOf(TwitchApiAuthenticationException.class)
          .hasMessage("message");
    }
  }
}
