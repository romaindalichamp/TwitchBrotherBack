package com.twitchbrother.back.exception;

import com.twitchbrother.back.service.TwitchService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitchApiAuthenticationException extends RuntimeException {
  private static final Logger LOG = LoggerFactory.getLogger(TwitchApiAuthenticationException.class);

  public TwitchApiAuthenticationException(String message, int waitBeforeNextAuthenticationTry) {
    super(message);
    LOG.error("Error in with Twitch API Authentication Waiting for {} seconds before to try again",
        waitBeforeNextAuthenticationTry);
    LOG.error(message);
  }
}

