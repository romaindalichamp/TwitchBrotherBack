package com.twitchbrother.back.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.twitchbrother.back.dto.GamesInformationsDto;
import com.twitchbrother.back.dto.GamesInformationsListDto;
import com.twitchbrother.back.service.DataSnapshotService;
import java.util.ArrayList;
import java.util.Arrays;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ExtendWith(SpringExtension.class)
@WebMvcTest(
    controllers = DataSnapshotControllerImpl.class
)
@Import({
    DataSnapshotControllerImpl.class
})
public class DataSnapShotControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private DataSnapshotService dataSnapshotService;

  @Test
  @DisplayName("HappyPath")
  public void saveGameInformationsHappyPathTest() throws Exception {
    GamesInformationsListDto gamesInformationsListDto =
        new GamesInformationsListDto(
            new ArrayList<>(Arrays.asList(
                new GamesInformationsDto(10, "Rainbow", 500))));

    when(
        dataSnapshotService.saveGameInformations(
            any(GamesInformationsListDto.class))).thenReturn(gamesInformationsListDto);

    mockMvc.perform(
            MockMvcRequestBuilders.put("/datasnapshot")
                .content(new ObjectMapper().writeValueAsString(gamesInformationsListDto))
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().is2xxSuccessful());

    verify(dataSnapshotService).saveGameInformations(any());
  }

  @Test
  @DisplayName("400 - invalid Payload")
  public void saveGameInformationsPayloadTest() throws Exception {
    GamesInformationsListDto gamesInformationsListDto = null;

    when(
        dataSnapshotService.saveGameInformations(
            any(GamesInformationsListDto.class))).thenReturn(gamesInformationsListDto);

    mockMvc.perform(
            MockMvcRequestBuilders.put("/datasnapshot")
                .content("{qweqwe-qweqweqw}")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
        .andExpect(MockMvcResultMatchers.status().is4xxClientError());

    verify(dataSnapshotService, times(0)).saveGameInformations(any());
  }
}
