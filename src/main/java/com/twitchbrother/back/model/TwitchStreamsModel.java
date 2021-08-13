package com.twitchbrother.back.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class TwitchStreamsModel {

  private ArrayList<TwitchStreamsDataModel> data = new ArrayList<>();
  private TwitchStreamsPaginationModel pagination = new TwitchStreamsPaginationModel();
}
