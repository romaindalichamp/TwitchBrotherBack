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

@ConfigurationProperties(prefix = "app")
@PropertySource("classpath:application.properties")
@Generated
@Data
@Accessors
@ToString
@Validated
@ConstructorBinding
@RequiredArgsConstructor
public class CustomConfigurationProperties {

  private final Redis redis;
  private final Twitch twitch;

  @Generated
  @Data
  @Accessors
  @ToString
  @Validated
  @ConstructorBinding
  @RequiredArgsConstructor
  public static class Redis {

    private final String hostname;
    private final Integer port;
  }

  @Generated
  @Data
  @Accessors
  @ToString
  @Validated
  @ConstructorBinding
  @RequiredArgsConstructor
  public static class Twitch {

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

      @Generated
      @Data
      @Accessors
      @ToString
      @Validated
      @ConstructorBinding
      @RequiredArgsConstructor
      public static class TwitchHelix {

        private final TwitchStreams streams;
        private final Authentication authentication;

        @Generated
        @Data
        @Accessors
        @ToString
        @Validated
        @ConstructorBinding
        @RequiredArgsConstructor
        public static class Authentication {

          private final String url;
          private final String clientidHeaderKey;
          private final TwitchConnectionData clientid;
          private final TwitchConnectionData clientsecret;
          private final TwitchConnectionData granttype;
          private final TwitchConnectionData scopes;
          private final String authorizationkey;

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
        }

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
}
