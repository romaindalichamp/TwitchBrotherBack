package com.twitchbrother.back.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitchApiRequestException extends RuntimeException {
  private static final Logger LOG = LoggerFactory.getLogger(TwitchApiRequestException.class);

  public TwitchApiRequestException(Exception e) {
    super("Something Happened during the Request to Twitch API");
    LOG.error("Something Happened during the Request to Twitch API");
    LOG.error(e.getMessage());
  }

  public TwitchApiRequestException(String message, Exception e) {
    super(message);
    LOG.error(message);
    LOG.error(e.getMessage());
  }
}

