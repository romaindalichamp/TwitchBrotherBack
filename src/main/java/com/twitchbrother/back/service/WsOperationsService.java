package com.twitchbrother.back.service;

import com.twitchbrother.back.mapper.StreamMapper;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class WsOperationsService {

  private final SimpMessageSendingOperations simpMessageSendingOperations;
  private final StreamMapper streamMapper;

  public WsOperationsService(
      SimpMessageSendingOperations simpMessageSendingOperations,
      StreamMapper streamMapper) {
    this.simpMessageSendingOperations = simpMessageSendingOperations;
    this.streamMapper = streamMapper;
  }

  /**
   * Uses Generics to send any object on a websocket with a given Topic / Destination
   * @param destination - URI where the message has to be send
   * @param message - the Object of any type to send
   * @param <T> - the type of the object can be any of T
   */
  public <T> void sendMessageOverWs(String destination, T message){
    this.simpMessageSendingOperations.convertAndSend(destination, message);
  }
}
