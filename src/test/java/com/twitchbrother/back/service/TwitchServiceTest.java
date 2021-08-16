package com.twitchbrother.back.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.twitchbrother.back.CustomConfigurationProperties;
import com.twitchbrother.back.gateway.TwitchAPIClient;
import com.twitchbrother.back.mapper.StreamMapper;
import com.twitchbrother.back.model.TwitchStreamsDataModel;
import com.twitchbrother.back.model.TwitchStreamsModel;
import com.twitchbrother.back.model.TwitchStreamsPaginationModel;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = CustomConfigurationProperties.class)
@TestPropertySource("classpath:application.properties")
@ContextConfiguration(classes = {CustomConfigurationProperties.class})
public class TwitchServiceTest {

  private TwitchService twitchService;

  @Autowired
  CustomConfigurationProperties customConfigurationProperties;

  @Mock
  private SimpMessageSendingOperations simpleSimpMessageSendingOperations;
  @Mock
  private TwitchAPIClient twitchAPIClient;
  @Mock
  private StreamMapper streamMapper;
  @Mock
  private WsOperationsService wsOperationsService;
  @Mock
  private RestTemplate restTemplate;

  @BeforeEach
  public void loadProperties() {
    twitchService = new TwitchService(customConfigurationProperties, wsOperationsService,
        twitchAPIClient, streamMapper);
  }

  @Test
  @DisplayName("pollHelixStreams")
  public void pollHelixStreams() {
    TwitchStreamsDataModel twitchStreamsDataModel = new TwitchStreamsDataModel();
    twitchStreamsDataModel.setGame_id("5");
    twitchStreamsDataModel.setLanguage("français");
    TwitchStreamsPaginationModel twitchStreamsPaginationModel = new TwitchStreamsPaginationModel();
    twitchStreamsPaginationModel.setCursor("");
    TwitchStreamsModel twitchStreamsModel = new TwitchStreamsModel();
    twitchStreamsModel.setData(new ArrayList<>(Arrays.asList(twitchStreamsDataModel)));
    twitchStreamsModel.setPagination(twitchStreamsPaginationModel);


    doNothing().when(wsOperationsService).sendMessageOverWs(anyString(), any(Object.class));
    when(twitchAPIClient.pollHelixStream(any())).thenReturn(twitchStreamsModel);

    this.twitchService.pollHelixStreams();

    verify(twitchAPIClient,times(2)).pollHelixStream(anyString());
  }
}