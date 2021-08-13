package com.twitchbrother.back;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@ConfigurationProperties(prefix = "twitch")
@PropertySource("classpath:application.properties")
@Generated
@Data
@Accessors
@ToString
@Validated
@ConstructorBinding
@RequiredArgsConstructor
public class TwitchConfigurationProperties {

  private final TwitchApi api;

  @Generated
  @Data
  @Accessors
  @ToString
  @Validated
  @ConstructorBinding
  @RequiredArgsConstructor
  public static class TwitchApi {

    private final String allowedOrigin;
    private final TwitchHelix helix;
    private final TwitchConnectionData authorization;
    private final TwitchConnectionData clientId;

    @Generated
    @Data
    @Accessors
    @ToString
    @Validated
    @ConstructorBinding
    @RequiredArgsConstructor
    public static class TwitchConnectionData {

      private final String key;
      private final String value;
    }

    @Generated
    @Data
    @Accessors
    @ToString
    @Validated
    @ConstructorBinding
    @RequiredArgsConstructor
    public static class TwitchHelix {

      private final TwitchStreams streams;

      @Generated
      @Data
      @Accessors
      @ToString
      @Validated
      @ConstructorBinding
      @RequiredArgsConstructor
      public static class TwitchStreams {

        private final String url;
        private final float throttle;
        private final String gameIdParameter;
        private final ArrayList<String> gameIds;
        private final String maxByPage;
        private final String afterParameter;

        public String createGameListRequestParameters() {
          return gameIds.stream().map((id) -> gameIdParameter.concat(id))
              .collect(Collectors.joining("&"));
        }
      }
    }
  }
}
