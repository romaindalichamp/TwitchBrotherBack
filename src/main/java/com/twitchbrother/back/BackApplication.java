package com.twitchbrother.back;

import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.io.IOException;
import org.springframework.context.annotation.Bean;
import org.springframework.messaging.simp.SimpMessageSendingOperations;

@SpringBootApplication
@EnableConfigurationProperties(TwitchConfigurationProperties.class)
public class BackApplication {
  private Map<String, Integer> progress = new HashMap<>();

  @Autowired
  private SimpMessageSendingOperations messagingTemplate;

  public static void main(String[] args) throws IOException, InterruptedException {
    SpringApplication.run(BackApplication.class, args);
  }

  /**
   * Generate random numbers publish with WebSocket protocol each 3 seconds.
   * @return a command line runner.
   */
  @Bean
  public CommandLineRunner websocketDemo() {
    return (args) -> {
      while (true) {
        try {
          Thread.sleep(3*1000); // Each 3 sec.
          progress.put("num1", randomWithRange(0, 100));
          progress.put("num2", randomWithRange(0, 100));
          messagingTemplate.convertAndSend("/topic/progress", this.progress);
          System.out.println("MESSAGE SENT");
        } catch (Exception e) {
          e.printStackTrace();
        }
      }
    };
  }

  /**
   * Get a random integer value in a min / max range.
   * @param min min range value
   * @param max max range value
   * @return A random integer value
   */
  private int randomWithRange(int min, int max)
  {
    int range = Math.abs(max - min) + 1;
    return (int)(Math.random() * range) + (min <= max ? min : max);
  }
}
