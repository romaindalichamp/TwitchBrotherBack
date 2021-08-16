package com.twitchbrother.back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import java.util.ArrayList;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class GamesInformationsListDto {

  @NotNull @Valid
  @JsonProperty("gamesGeneralInformations")
  private ArrayList<GamesInformationsDto> gamesGeneralInformations;
}
