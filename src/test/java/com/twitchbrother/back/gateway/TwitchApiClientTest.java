package com.twitchbrother.back.gateway;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import com.twitchbrother.back.CustomConfigurationProperties;
import com.twitchbrother.back.handler.RestTemplateErrorHandler;
import com.twitchbrother.back.mapper.StreamMapper;
import com.twitchbrother.back.service.TwitchService;
import com.twitchbrother.back.service.WsOperationsService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.client.RestTemplate;

@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = CustomConfigurationProperties.class)
@TestPropertySource("classpath:application.properties")
@ContextConfiguration(classes = {CustomConfigurationProperties.class})
public class TwitchApiClientTest {

  private TwitchAPIClient twitchAPIClient;

  @Autowired
  CustomConfigurationProperties customConfigurationProperties;

  @Mock
  private RestTemplateBuilder restTemplateBuilder;
  @Mock
  private RestTemplateErrorHandler restTemplateErrorHandler;
  @Mock
  private RestTemplate restTemplate;

  @BeforeEach
  public void loadProperties() {
  }

  @Test
  @DisplayName("pollHelixStream")
  public void pollHelixStream(){
  }
}