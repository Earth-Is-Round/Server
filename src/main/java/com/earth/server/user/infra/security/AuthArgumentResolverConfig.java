package com.earth.server.user.infra.security;


import lombok.RequiredArgsConstructor;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@RequiredArgsConstructor
class AuthArgumentResolverConfig implements WebMvcConfigurer {
  private final HandlerMethodArgumentResolver handlerMethodArgumentResolver;

  @Override
  public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
    resolvers.add(handlerMethodArgumentResolver);
  }
}
