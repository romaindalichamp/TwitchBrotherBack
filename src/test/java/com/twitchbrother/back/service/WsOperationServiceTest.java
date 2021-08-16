package com.twitchbrother.back.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.twitchbrother.back.dto.GamesInformationsDto;
import com.twitchbrother.back.dto.GamesInformationsListDto;
import com.twitchbrother.back.dto.StreamDataDto;
import com.twitchbrother.back.mapper.GamesInformationsMapper;
import com.twitchbrother.back.mapper.StreamMapper;
import com.twitchbrother.back.model.GamesInformationsList;
import com.twitchbrother.back.repository.GamesInformationsRepository;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class WsOperationServiceTest {

  @InjectMocks
  private WsOperationsService wsOperationsService;

  @Mock
  private SimpMessageSendingOperations simpMessageSendingOperations;
  @Mock
  private StreamMapper streamMapper;

  @Test
  @DisplayName("ConvertAndSend")
  public void convertAndSendDatas() {
    doNothing().when(simpMessageSendingOperations).convertAndSend(anyString(),any(Object.class));

    this.wsOperationsService.sendMessageOverWs("",new StreamDataDto());

    verify(simpMessageSendingOperations).convertAndSend(anyString(),any(Object.class));
  }
}