package com.twitchbrother.back;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootApplication
@EnableConfigurationProperties(CustomConfigurationProperties.class)
public class BackApplication {

  @Value("${app.redis.hostname}")
  private String redisHostname;

  @Value("${app.redis.port}")
  private int redisPort;

  public static void main(String[] args) throws IOException, InterruptedException {
    SpringApplication.run(BackApplication.class, args);
  }

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration conf = new RedisStandaloneConfiguration();
    conf.setHostName(redisHostname);
    conf.setPort(redisPort);
    return new JedisConnectionFactory(conf);
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());
    return template;
  }
}
