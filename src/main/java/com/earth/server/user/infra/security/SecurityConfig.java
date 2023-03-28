package com.earth.server.user.infra.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
class SecurityConfig {
  @Bean
  AccessDeniedHandler accessDeniedHandler(ObjectMapper mapper) {
    return new DeniedExceptionHandler(mapper);
  }

  @Bean
  AuthenticationEntryPoint authenticationEntryPoint(ObjectMapper mapper) {
    return new EntryPointExceptionHandler(mapper);
  }

  @Bean
  HandlerMethodArgumentResolver handlerMethodArgumentResolver() {
    return new AuthArgumentResolver();
  }

  @Bean
  WebMvcConfigurer webMvcConfigurer(HandlerMethodArgumentResolver handlerMethodArgumentResolver) {
    return new AuthArgumentResolverConfig(handlerMethodArgumentResolver);
  }

  @Bean
  public SecurityFilterChain filterChain(
    HttpSecurity http,
    AccessDeniedHandler accessDeniedHandler,
    AuthenticationEntryPoint authenticationEntryPoint) throws Exception {
    return http.csrf().disable()

      .exceptionHandling(
        e -> e.accessDeniedHandler(accessDeniedHandler).authenticationEntryPoint(authenticationEntryPoint))

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
