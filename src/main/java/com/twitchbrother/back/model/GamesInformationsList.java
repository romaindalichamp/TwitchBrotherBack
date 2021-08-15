package com.twitchbrother.back.model;

import com.twitchbrother.back.dto.GamesInformationsDto;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.UUID;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("GamesInformations")
public class GamesInformationsList implements Serializable {

  @Id
  private String serialVersionUID = UUID.randomUUID().toString();
  private ArrayList<GamesInformationsDto> gamesGeneralInformations;
}