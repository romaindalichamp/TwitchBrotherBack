package com.twitchbrother.back.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class TwitchStreamsPaginationModel {

  private String cursor;
}