package com.twitchbrother.back.dto;

import java.util.List;
import lombok.Builder;
import lombok.Data;

/**
 * Model with Builder Pattern
 */
@Data
@Builder
public class StreamDto {

  private String gameList;
  private Integer totalViewers;
  private List<StreamDataDto> data;
}
