package com.twitchbrother.back.service;

import com.twitchbrother.back.TwitchConfigurationProperties;
import com.twitchbrother.back.gateway.TwitchAPIClient;
import com.twitchbrother.back.mapper.StreamMapper;
import com.twitchbrother.back.model.TwitchStreamsDataModel;
import com.twitchbrother.back.model.TwitchStreamsModel;
import com.twitchbrother.back.util.TwitchUtils;
import java.util.Objects;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Service;

@Service
public class TwitchService {

  private static final Logger LOG = LoggerFactory.getLogger(TwitchService.class);

  private static final int MAXIMUM_PAGES_BY_THREAD = 25;
  private final TwitchConfigurationProperties twitchConfigurationProperties;
  private final SimpMessageSendingOperations simpleSimpMessageSendingOperations;
  private final WsOperationsService wsOperationsService;
  private final TwitchAPIClient twitchAPIClient;
  private final StreamMapper streamMapper;
  private final float twitchThrottle;
  private float numberOfRequestForPagination;

  public TwitchService(SimpMessageSendingOperations simpleSimpMessageSendingOperations,
      TwitchConfigurationProperties twitchConfigurationProperties,
      WsOperationsService wsOperationsService,
      TwitchAPIClient twitchAPIClient, StreamMapper streamMapper) {

    this.twitchConfigurationProperties = twitchConfigurationProperties;
    this.simpleSimpMessageSendingOperations = simpleSimpMessageSendingOperations;
    this.wsOperationsService = wsOperationsService;
    this.twitchAPIClient = twitchAPIClient;
    this.streamMapper = streamMapper;

    twitchThrottle =
        this.twitchConfigurationProperties.getApi().getHelix().getStreams().getThrottle();

    this.pollHelixStreamsWithThread();
  }

  /**
   * Short Polling Twitch with Thread Implementation
   */
  public void pollHelixStreamsWithThread() {
    Thread thread = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {

        TwitchStreamsModel twitchStreamsModel = this.pollHelixStreams();

        try {
          Thread.sleep((long)
              TwitchUtils.calculatethrottleAfterEveryRequest(
                  numberOfRequestForPagination, twitchThrottle));
        } catch (InterruptedException e) {
          LOG.warn("Interrupted ! {}", e.toString());
          Thread.currentThread().interrupt();
        }

        this.convertAndSendData(twitchStreamsModel);
      }
    });
    thread.start();
  }

  private void convertAndSendData(TwitchStreamsModel twitchStreamsModel) {
    this.wsOperationsService.sendMessageOverWs("/streams/progress",streamMapper.map(twitchStreamsModel));
  }

  /**
   * Poll multiple games on Twitch API on a maximum of {@value TwitchService#MAXIMUM_PAGES_BY_THREAD}
   * pages
   * @return
   */
  private TwitchStreamsModel pollHelixStreams() {
    TwitchStreamsModel twitchStreamsModel = new TwitchStreamsModel();
    String cursor = "";
    this.numberOfRequestForPagination = 0;

    do {
      TwitchStreamsModel result = twitchAPIClient.pollHelixStream(cursor);
      cursor = result.getPagination().getCursor();
      numberOfRequestForPagination++;
      twitchStreamsModel.getData().addAll(result.getData());
    } while (Objects.nonNull(cursor) && numberOfRequestForPagination < MAXIMUM_PAGES_BY_THREAD);

    LOG.info("Poll Streams for the following games: {}",
        twitchStreamsModel.getData().stream().map(TwitchStreamsDataModel::getGame_name).distinct()
            .collect(Collectors.toList()));
    LOG.info("Total number of viewers: {}",
        twitchStreamsModel.getData().stream().map(TwitchStreamsDataModel::getViewer_count)
            .mapToInt(Integer::intValue).sum());

    return twitchStreamsModel;
  }
}
