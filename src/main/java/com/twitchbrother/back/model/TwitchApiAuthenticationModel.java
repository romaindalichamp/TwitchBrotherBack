package com.twitchbrother.back.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.util.ArrayList;
import lombok.Data;

@Data
@JsonIgnoreProperties
public class TwitchApiAuthenticationModel {

    private String access_token;
    private Integer expires_in;
    private ArrayList<String> scope;
    private String token_type;
  }