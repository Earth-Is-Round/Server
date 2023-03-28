package com.earth.server.user.infra.security.jwt;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.SecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.DefaultSecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@RequiredArgsConstructor
public class FilterConfig extends SecurityConfigurerAdapter<DefaultSecurityFilterChain, HttpSecurity> {
  private final JwtResolver jwtResolver;
  private final ObjectMapper mapper;

  @Override
  public void configure(HttpSecurity http) throws Exception {
    JwtFilter filter = new JwtFilter(jwtResolver);
    FilterExceptionHandler exceptionHandler = new FilterExceptionHandler(mapper);
    http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    http.addFilterBefore(exceptionHandler, JwtFilter.class);
  }
}
