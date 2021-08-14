package com.twitchbrother.back.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TwitchApiRequestException extends RuntimeException {
  private static final Logger LOG = LoggerFactory.getLogger(TwitchApiRequestException.class);

  public TwitchApiRequestException(Throwable e) {
    super("Something Happened during the Request to Twitch API");
    this.processException("Something Happened during the Request to Twitch API", e);
  }

  public TwitchApiRequestException(String message, Throwable e) {
    super(message);
    this.processException(message, e);
  }

  private void processException(String message,Throwable e){
    LOG.error(message);
    LOG.error(e.getMessage());
  }
}

