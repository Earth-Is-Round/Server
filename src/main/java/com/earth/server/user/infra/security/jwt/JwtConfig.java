package com.earth.server.user.infra.security.jwt;

import com.earth.server.user.domain.GenerateToken;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JwtConfig {
  @Bean
  JwtSecretKey jwtSecretKey() {
    return new JwtSecretKey();
  }

  @Bean
  JwtResolver jwtResolver(JwtSecretKey jwtSecretKey) {
    return new JwtResolver(jwtSecretKey);
  }

  @Bean
  GenerateToken generateToken(JwtSecretKey jwtSecretKey) {
    return new JwtProvider(jwtSecretKey);
  }
}
