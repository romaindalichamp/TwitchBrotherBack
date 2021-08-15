package com.twitchbrother.back.controller;

import com.twitchbrother.back.dto.GamesInformationsListDto;
import com.twitchbrother.back.service.DataSnapshotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataSnapshotControllerImpl implements DataSnapShotController {

  private static final Logger LOG = LoggerFactory.getLogger(DataSnapshotControllerImpl.class);

  private final DataSnapshotService dataSnapshotService;

  public DataSnapshotControllerImpl(DataSnapshotService dataSnapshotService) {
    this.dataSnapshotService = dataSnapshotService;
  }

  public GamesInformationsListDto saveGameInformations(
      GamesInformationsListDto gamesInformationsListDto) {
    try {
      return this.dataSnapshotService.saveGameInformations(gamesInformationsListDto);
    } catch (Exception e) {
      LOG.error("An error occured during data saving {}", e.getMessage());
      throw new RuntimeException();
    }
  }
}
