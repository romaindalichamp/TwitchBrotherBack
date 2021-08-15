package com.twitchbrother.back.service;

import com.twitchbrother.back.dto.GamesInformationsListDto;
import com.twitchbrother.back.mapper.GamesInformationsMapper;
import com.twitchbrother.back.repository.GamesInformationsRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Map and save datas into Redis Server
 */
@Service
public class DataSnapshotService {

  private static final Logger LOG = LoggerFactory.getLogger(DataSnapshotService.class);

  private final GamesInformationsRepository gamesInformationsRepository;
  private final GamesInformationsMapper gamesInformationsMapper;

  public DataSnapshotService(
      GamesInformationsRepository gamesInformationsRepository,
      GamesInformationsMapper gamesInformationsMapper) {
    this.gamesInformationsRepository = gamesInformationsRepository;
    this.gamesInformationsMapper = gamesInformationsMapper;
  }

  public GamesInformationsListDto saveGameInformations(
      GamesInformationsListDto gamesInformationsListDto) {
    LOG.debug("Saving to redis: {}", gamesInformationsListDto);

    return this.gamesInformationsMapper.mapTo(
        this.gamesInformationsRepository.save(
            this.gamesInformationsMapper.map(gamesInformationsListDto)));
  }
}
