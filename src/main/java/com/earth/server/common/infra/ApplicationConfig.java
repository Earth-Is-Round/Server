package com.earth.server.common.infra;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class ApplicationConfig {
  @Bean
  ObjectMapper objectMapper() {
    return new ObjectMapper();
  }
}
