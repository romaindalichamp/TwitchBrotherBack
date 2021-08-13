package com.twitchbrother.back.dto;

import lombok.Builder;
import lombok.Data;

/**
 * Model with Builder Pattern
 */
@Data
@Builder
public class GamePollDto {

  private Integer gameId;
  private String gameName;
  private Integer totalViewers;
}
