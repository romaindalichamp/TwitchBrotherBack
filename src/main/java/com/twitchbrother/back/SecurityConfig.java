package com.twitchbrother.back;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private final TwitchConfigurationProperties twitchConfigurationProperties;

  public SecurityConfig(
      TwitchConfigurationProperties twitchConfigurationProperties) {
    this.twitchConfigurationProperties = twitchConfigurationProperties;
  }

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .headers()
        .frameOptions().disable()
        .and()
        .csrf().disable()
        .authorizeRequests()
        .antMatchers("/ws").permitAll()
        .anyRequest()
        .authenticated();
  }

  /**
   * Apply CORS configuration before Spring Security. By default, "http.cors" take a bean called
   * corsConfigurationSource.
   *
   * @return a CORS configuration source.
   * @implNote https://docs.spring.io/spring-security/site/docs/current/reference/htmlsingle/#cors
   */
  @Bean
  public CorsConfigurationSource corsConfigurationSource() {
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    CorsConfiguration config = new CorsConfiguration();
    config.setAllowCredentials(false);
    config.addAllowedOrigin(twitchConfigurationProperties.getApi().getAllowedOrigin());
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
