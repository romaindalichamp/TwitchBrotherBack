package com.twitchbrother.back.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@AllArgsConstructor
@NoArgsConstructor
public class GamesInformationsDto {

  @Valid
  @NotNull
  @JsonProperty("game_id")
  private Integer game_id;

  @Valid
  @NotNull
  @JsonProperty("game_name")
  private String game_name;

  @Valid
  @NotNull
  @JsonProperty("viewers")
  private Integer viewers;
}
