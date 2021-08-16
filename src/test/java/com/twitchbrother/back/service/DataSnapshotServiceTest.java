package com.twitchbrother.back.service;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.twitchbrother.back.dto.GamesInformationsDto;
import com.twitchbrother.back.dto.GamesInformationsListDto;
import com.twitchbrother.back.mapper.GamesInformationsMapper;
import com.twitchbrother.back.model.GamesInformationsList;
import com.twitchbrother.back.repository.GamesInformationsRepository;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
public class DataSnapshotServiceTest {

  @InjectMocks
  private DataSnapshotService dataSnapshotService;

  @Mock
  private GamesInformationsRepository gamesInformationsRepository;
  @Mock
  private GamesInformationsMapper gamesInformationsMapper;

  @Test
  @DisplayName("saveGameInformations")
  public void saveGameInformations() {
    GamesInformationsListDto gamesInformationsListDto =
        new GamesInformationsListDto(
            new ArrayList<>(Arrays.asList(
                new GamesInformationsDto(10, "Rainbow", 500))));
    when(gamesInformationsRepository.save(any())).thenReturn(new GamesInformationsList());
    when(gamesInformationsMapper.map(any(GamesInformationsListDto.class))).thenReturn(new GamesInformationsList());
    when(gamesInformationsMapper.mapTo(any(GamesInformationsList.class))).thenReturn(new GamesInformationsListDto());

    this.dataSnapshotService.saveGameInformations(gamesInformationsListDto);

    verify(gamesInformationsRepository).save(any());
    verify(gamesInformationsMapper).map(any(GamesInformationsListDto.class));
    verify(gamesInformationsMapper).mapTo(any(GamesInformationsList.class));
  }
}