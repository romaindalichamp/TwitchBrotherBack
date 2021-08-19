package com.twitchbrother.back.service;

import com.twitchbrother.back.CustomConfigurationProperties;
import com.twitchbrother.back.gateway.TwitchAPIClient;
import com.twitchbrother.back.mapper.StreamMapper;
import com.twitchbrother.back.model.TwitchStreamsDataModel;
import com.twitchbrother.back.model.TwitchStreamsModel;
import com.twitchbrother.back.util.TwitchUtils;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class TwitchService {

  private static final Logger LOG = LoggerFactory.getLogger(TwitchService.class);

  private static final int MAXIMUM_PAGES_BY_THREAD = 80;
  private final CustomConfigurationProperties customConfigurationProperties;
  private final WsOperationsService wsOperationsService;
  private final TwitchAPIClient twitchAPIClient;
  private final StreamMapper streamMapper;
  private final float twitchThrottle;
  private float numberOfRequestForPagination;
  private long timeToConsultAllPaginations;

  public TwitchService(CustomConfigurationProperties customConfigurationProperties,
      WsOperationsService wsOperationsService,
      TwitchAPIClient twitchAPIClient, StreamMapper streamMapper) {

    this.customConfigurationProperties = customConfigurationProperties;
    this.wsOperationsService = wsOperationsService;
    this.twitchAPIClient = twitchAPIClient;
    this.streamMapper = streamMapper;

    twitchThrottle =
        this.customConfigurationProperties.getTwitch().getApi().getHelix().getStreams()
            .getThrottle();

    this.pollHelixStreamsWithThread();
  }

  /**
   * Short Polling Twitch with Thread Implementation looping forever The Throttle fixed by Twitch is
   * respected using a simple algorithm calculating the average time per request and waiting a bit
   * if necessary to refill let Twitch Bucket refill
   */
  public void pollHelixStreamsWithThread() {
    Executors.newCachedThreadPool().execute(() -> {
      while (!Thread.currentThread().isInterrupted()) {

        TwitchStreamsModel twitchStreamsModel = this.pollHelixStreams();

        try {
          // Ratelimit-Remaining: 799 Header stays at 799 requests and never changes.
          Thread.sleep((long)
              TwitchUtils.calculatethrottleAfterEveryRequest(
                  numberOfRequestForPagination, twitchThrottle, timeToConsultAllPaginations));
        } catch (InterruptedException e) {
          LOG.warn("Interrupted ! {}", e.toString());
          Thread.currentThread().interrupt();
        }

        this.convertAndSendData(twitchStreamsModel);
      }
    });
  }

  private void convertAndSendData(TwitchStreamsModel twitchStreamsModel) {
    this.wsOperationsService.sendMessageOverWs("/streams/progress",
        streamMapper.map(twitchStreamsModel));
  }

  /**
   * Poll multiple games on Twitch API on a maximum of {@value TwitchService#MAXIMUM_PAGES_BY_THREAD}
   * pages
   *
   * @return
   */
  public TwitchStreamsModel pollHelixStreams() {
    TwitchStreamsModel twitchStreamsModel = new TwitchStreamsModel();
    String cursor = "";
    this.numberOfRequestForPagination = 0;

    Long startTime = System.currentTimeMillis();

    do {
      TwitchStreamsModel result = twitchAPIClient.pollHelixStream(cursor);
      cursor = result.getPagination().getCursor();
      numberOfRequestForPagination++;
      twitchStreamsModel.getData().addAll(result.getData());
    } while (Objects.nonNull(cursor) && !cursor.isEmpty() && numberOfRequestForPagination < MAXIMUM_PAGES_BY_THREAD);

    Long endTime = System.currentTimeMillis();

    this.timeToConsultAllPaginations = endTime - startTime;

    LOG.debug("difference {} - {} = {}", endTime, startTime, timeToConsultAllPaginations);
    LOG.info("Poll Streams for the following games: {}",
        twitchStreamsModel.getData().stream().map(TwitchStreamsDataModel::getGame_name).distinct()
            .collect(Collectors.toList()));

    return twitchStreamsModel;
  }
}
