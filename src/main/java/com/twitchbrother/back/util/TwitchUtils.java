package com.twitchbrother.back.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Twitch Utils
 */
public final class TwitchUtils {
  private TwitchUtils() {}

  private static final Logger LOG = LoggerFactory.getLogger(TwitchUtils.class);

  /**
   * Calculate the minimum milliseconds number to wait after a group of request to the Twitch API in
   * order to respect the maximum Throttle authorized
   *
   * @param numberRequestExecuted    Number of request just previously executed
   * @param defaultThrottleByMinutes Maximum / Default Throttle from Twitch API
   * @return he minimum milliseconds number to wait after a group of request to the Twitch API
   */
  public static float calculatethrottleAfterEveryRequest(float numberRequestExecuted,
      float defaultThrottleByMinutes, long timeToConsultAllPaginations) {
    float beforeNextThread = 0;
    float twitchRefillSpeed = 1 / (defaultThrottleByMinutes/60);
    float averageMsByRequest = (timeToConsultAllPaginations / 1000F) / numberRequestExecuted;

    // if the server requests delay (ms) is higher than the API maximum authorized, there is no need to slow down the thread
    if (averageMsByRequest < twitchRefillSpeed) {
        beforeNextThread = twitchRefillSpeed*numberRequestExecuted; // wait for server refill
    }

    LOG.debug("Twitch Refill speed (ms): {}", twitchRefillSpeed);
    LOG.debug("Average (ms) By Request: {}", averageMsByRequest);
    LOG.debug("Number of paginations called: {}", numberRequestExecuted);
    LOG.debug("Waiting for {} ms before next Thread", beforeNextThread);
    return beforeNextThread;
  }
}
