package com.twitchbrother.back.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Twitch Utils
 */
public final class TwitchUtils {

  private static final Logger LOG = LoggerFactory.getLogger(TwitchUtils.class);

  /**
   * @param throttle max throttle fixed by the API
   * @return throttle foreach thread in milliseconds
   */
  public static int calculateMaxThrottle(final int throttle) {
    return 1000 / (throttle / 60);
  }

  /**
   * Calculate the minimum milliseconds number to wait after a group of request to the Twitch API in
   * order to respect the maximum Throttle authorized
   *
   * @param numberRequestExecuted    Number of request just previously executed
   * @param defaultThrottleByMinutes Maximum / Default Throttle from Twitch API
   * @return he minimum milliseconds number to wait after a group of request to the Twitch API
   */
  public static int calculatethrottleAfterEveryRequest(int numberRequestExecuted,
      int defaultThrottleByMinutes) {
    float maxRequestBySeconds = defaultThrottleByMinutes / 60;
    int beforeNextThread = calculateMaxThrottle(defaultThrottleByMinutes);

    if (numberRequestExecuted > maxRequestBySeconds) {
      beforeNextThread = (int) (numberRequestExecuted / maxRequestBySeconds) * 1000;
    }

    LOG.debug("Number of paginations called: {}", numberRequestExecuted);
    LOG.debug("Max Requests By Seconds: {}", maxRequestBySeconds);
    LOG.debug("Waiting for {} ms before next Thread", beforeNextThread);
    return beforeNextThread;
  }
}
