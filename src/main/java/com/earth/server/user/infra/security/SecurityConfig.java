package com.earth.server.user.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;

@EnableWebSecurity
public class SecurityConfig {

  @Bean
  AccessDeniedHandler accessDeniedHandler() {
    return new DeniedExceptionHandler();
  }

  @Bean
  AuthenticationEntryPoint authenticationEntryPoint() {
    return new EntryPointExceptionHandler();
  }

  @Bean
  public SecurityFilterChain filterChain(
    HttpSecurity http,
    AccessDeniedHandler accessDeniedHandler,
    AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
    return http.csrf().disable()

      .exceptionHandling()
      .accessDeniedHandler(accessDeniedHandler)
      .authenticationEntryPoint(authenticationEntryPoint)
      .and()

      .headers()
      .frameOptions()
      .disable()
      .and()

      .sessionManagement()
      .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
      .and()

      .authorizeHttpRequests(authorize -> authorize
        .requestMatchers("/users/login").permitAll()
        .requestMatchers("/users/signup").permitAll()
        .anyRequest().denyAll())

      .build();
  }
}
