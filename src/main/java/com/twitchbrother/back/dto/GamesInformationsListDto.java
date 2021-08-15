package com.twitchbrother.back.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.ArrayList;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class GamesInformationsListDto {

  @JsonProperty("gamesGeneralInformations")
  private ArrayList<GamesInformationsDto> gamesGeneralInformations;
}
