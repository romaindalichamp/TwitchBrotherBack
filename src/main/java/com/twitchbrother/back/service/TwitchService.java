package com.twitchbrother.back.service;

import com.twitchbrother.back.TwitchConfigurationProperties;
import com.twitchbrother.back.gateway.TwitchClient;
import com.twitchbrother.back.model.TwitchStreamsDataModel;
import com.twitchbrother.back.model.TwitchStreamsModel;
import com.twitchbrother.back.util.TwitchUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class TwitchService {

  private static final Logger LOG = LoggerFactory.getLogger(TwitchService.class);

  private static final int MAXIMUM_PAGES_BY_THREAD = 25;
  private final TwitchConfigurationProperties twitchConfigurationProperties;
  private final TwitchClient twitchClient;
  private final Integer twitchThrottle;
  private TwitchStreamsModel twitchStreamsModel = new TwitchStreamsModel();
  private int numberOfRequestForPagination;

  public TwitchService(TwitchConfigurationProperties twitchConfigurationProperties,
      TwitchClient twitchClient) {
    this.twitchConfigurationProperties = twitchConfigurationProperties;
    this.twitchClient = twitchClient;

    twitchThrottle = this.twitchConfigurationProperties.getApi().getHelix().getStreams()
        .getThrottle();
    pollHelixStreamsWithThread();
  }

  /**
   * Short Polling Twitch with Thread Implementation
   */
  public void pollHelixStreamsWithThread() {
    Thread thread = new Thread(() -> {
      while (!Thread.currentThread().isInterrupted()) {
        twitchStreamsModel = new TwitchStreamsModel();

        this.pollHelixStreams();

        try {
          Thread.sleep(TwitchUtils.calculatethrottleAfterEveryRequest(numberOfRequestForPagination,
              twitchThrottle));
        } catch (InterruptedException e) {
          LOG.warn("Interrupted ! {}", e.toString());
          Thread.currentThread().interrupt();
        }
      }
    });
    thread.start();
  }

  /**
   * Poll multiple games on Twitch API on a maximum of {@value TwitchService#MAXIMUM_PAGES_BY_THREAD}
   * pages
   */
  private void pollHelixStreams() {
    String cursor = "";
    this.numberOfRequestForPagination = 0;
    do {
      TwitchStreamsModel result = twitchClient.pollHelixStream(cursor);
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
  }
}
