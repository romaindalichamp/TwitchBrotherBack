package com.twitchbrother.back.dto;

import lombok.Data;

@Data
public class StreamDataDto {

  private String game_id;
  private String game_name;
  private Integer viewer_count;
}
