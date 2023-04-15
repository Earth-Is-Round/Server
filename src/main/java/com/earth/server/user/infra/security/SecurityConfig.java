package com.earth.server.user.infra.security;

import com.earth.server.user.infra.security.jwt.FilterConfig;
import com.earth.server.user.infra.security.jwt.JwtResolver;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebSecurity
@Configuration
@RequiredArgsConstructor
class SecurityConfig {
  private final JwtResolver jwtResolver;
  private final ObjectMapper objectMapper;

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
  PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
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
        .requestMatchers("/apis/users/login").permitAll()
        .requestMatchers("/apis/users/signup").permitAll()
        .requestMatchers("/apis/**").authenticated()
        .anyRequest().denyAll())
      .apply(new FilterConfig(jwtResolver, objectMapper))
      .and()

      .build();
  }
}
