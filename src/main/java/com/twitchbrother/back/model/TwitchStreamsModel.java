package com.twitchbrother.back.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.ArrayList;

@Data
@JsonIgnoreProperties
public class TwitchStreamsModel {

  private ArrayList<TwitchStreamsDataModel> data = new ArrayList<>();
  private TwitchStreamsPaginationModel pagination = new TwitchStreamsPaginationModel();
}
