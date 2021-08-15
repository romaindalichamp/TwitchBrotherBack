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

  private final CustomConfigurationProperties customConfigurationProperties;

  public SecurityConfig(
      CustomConfigurationProperties customConfigurationProperties) {
    this.customConfigurationProperties = customConfigurationProperties;
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
        .antMatchers("/datasnapshot").permitAll()
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
    config.addAllowedOrigin(customConfigurationProperties.getTwitch().getApi().getAllowedOrigin());
    config.addAllowedHeader("*");
    config.addAllowedMethod("*");
    source.registerCorsConfiguration("/**", config);
    return source;
  }
}
