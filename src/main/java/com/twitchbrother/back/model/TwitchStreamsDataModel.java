package com.twitchbrother.back.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties
public class TwitchStreamsDataModel {

  private String id;
  private String user_id;
  private String user_login;
  private String user_name;
  private String game_id;
  private String game_name;
  private String type;
  private String title;
  private Integer viewer_count;
  private String started_at;
  private String language;
  private String thumbnail_url;
  private ArrayList<String> tag_ids;
  private Boolean is_mature;
}