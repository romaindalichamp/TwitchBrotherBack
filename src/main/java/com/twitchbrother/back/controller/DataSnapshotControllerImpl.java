package com.twitchbrother.back.controller;

import com.twitchbrother.back.dto.GamesInformationsListDto;
import com.twitchbrother.back.service.DataSnapshotService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
public class DataSnapshotControllerImpl implements DataSnapShotController {

  private static final Logger LOG = LoggerFactory.getLogger(DataSnapshotControllerImpl.class);

  private final DataSnapshotService dataSnapshotService;

  public DataSnapshotControllerImpl(DataSnapshotService dataSnapshotService) {
    this.dataSnapshotService = dataSnapshotService;
  }

  public GamesInformationsListDto saveGameInformations(
      final GamesInformationsListDto gamesInformationsListDto) {
    return this.dataSnapshotService.saveGameInformations(gamesInformationsListDto);
  }
}
