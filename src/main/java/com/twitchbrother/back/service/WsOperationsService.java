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

  public <T> void sendMessageOverWs(String destination, T message){
    this.simpMessageSendingOperations.convertAndSend(destination, message);
  }
}
