package com.twitchbrother.back.model;

import java.io.Serializable;
import lombok.Data;
import org.springframework.data.redis.core.RedisHash;

@Data
@RedisHash("GamesInformations")
public class GamesInformations implements Serializable {

  private String gameId;
  private String gameName;
  private String viewers;
}
