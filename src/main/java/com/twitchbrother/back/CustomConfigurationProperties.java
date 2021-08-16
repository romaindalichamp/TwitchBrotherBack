package com.twitchbrother.back;

import java.util.ArrayList;
import java.util.stream.Collectors;
import lombok.Data;
import lombok.Generated;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.boot.context.properties.ConstructorBinding;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.validation.annotation.Validated;

@ConfigurationPropertiesScan
@EnableConfigurationProperties
@Configuration
@ConfigurationProperties(prefix = "app")
@PropertySource("classpath:application.properties")
@Generated
@Data
@Accessors(chain = true)
@ToString
@Validated
@RequiredArgsConstructor
public class CustomConfigurationProperties {

  private Redis redis;
  private Twitch twitch;

  @Generated
  @Data
  @Accessors
  @ToString
  @Validated
  @ConstructorBinding
  @RequiredArgsConstructor
  public static class Redis {

    private String hostname;
    private Integer port;
  }

  @Generated
  @Data
  @Accessors
  @ToString
  @Validated
  @ConstructorBinding
  @RequiredArgsConstructor
  public static class Twitch {

    private TwitchApi api;


    @Generated
    @Data
    @Accessors
    @ToString
    @Validated
    @ConstructorBinding
    @RequiredArgsConstructor
    public static class TwitchApi {

      private String allowedOrigin;
      private TwitchHelix helix;

      @Generated
      @Data
      @Accessors
      @ToString
      @Validated
      @ConstructorBinding
      @RequiredArgsConstructor
      public static class TwitchHelix {

        private TwitchStreams streams;
        private Authentication authentication;

        @Generated
        @Data
        @Accessors
        @ToString
        @Validated
        @ConstructorBinding
        @RequiredArgsConstructor
        public static class Authentication {

          private String url;
          private String clientidHeaderKey;
          private TwitchConnectionData clientid;
          private TwitchConnectionData clientsecret;
          private TwitchConnectionData granttype;
          private TwitchConnectionData scopes;
          private String authorizationkey;

          @Generated
          @Data
          @Accessors
          @ToString
          @Validated
          @ConstructorBinding
          @RequiredArgsConstructor
          public static class TwitchConnectionData {

            private String key;
            private String value;
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

          private String url;
          private float throttle;
          private String gameIdParameter;
          private ArrayList<String> gameIds;
          private String maxByPage;
          private String afterParameter;

          public String createGameListRequestParameters() {
            return gameIds.stream().map((id) -> gameIdParameter.concat(id))
                .collect(Collectors.joining("&"));
          }
        }
      }
    }
  }
}
