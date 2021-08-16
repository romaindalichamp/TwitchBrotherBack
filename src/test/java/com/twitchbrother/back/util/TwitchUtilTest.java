package com.twitchbrother.back.util;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class TwitchUtilTest {

  @Test
  @DisplayName("calculatethrottleAfterEveryRequest")
  public void calculatethrottleAfterEveryRequest() {
    float numberRequestExecuted = 25;
    float defaultThrottleByMinutes = 800;
    long timeToConsultAllPaginations = 3000;

    Assertions.assertThat(
            TwitchUtils.calculatethrottleAfterEveryRequest(
                numberRequestExecuted, defaultThrottleByMinutes, timeToConsultAllPaginations))
        .isEqualTo(0.0f);
  }

  @Test
  @DisplayName("calculatethrottleAfterEveryRequestWait")
  public void calculatethrottleAfterEveryRequestWait() {
    float numberRequestExecuted = 25;
    float defaultThrottleByMinutes = 800;
    long timeToConsultAllPaginations = 500;

    float resultat = TwitchUtils.calculatethrottleAfterEveryRequest(
        numberRequestExecuted, defaultThrottleByMinutes, timeToConsultAllPaginations);

    System.out.println(resultat);

    Assertions.assertThat(
            TwitchUtils.calculatethrottleAfterEveryRequest(
                numberRequestExecuted, defaultThrottleByMinutes, timeToConsultAllPaginations))
        .isNotEqualTo(0.0);

  }

  @Test
  @DisplayName("constructorPrivate")
  public void constructorPrivate() throws InstantiationException, IllegalAccessException {
    Assertions.assertThatThrownBy(() -> TwitchUtils.class.newInstance())
        .isExactlyInstanceOf(IllegalAccessException.class);
  }
}
