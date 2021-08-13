package com.twitchbrother.back;

import java.io.IOException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(TwitchConfigurationProperties.class)
public class BackApplication {

  public static void main(String[] args) throws IOException, InterruptedException {
    SpringApplication.run(BackApplication.class, args);
  }
}
