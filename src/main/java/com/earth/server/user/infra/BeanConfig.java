package com.earth.server.user.infra;

import com.earth.server.user.domain.GenerateToken;
import com.earth.server.user.domain.LoginUseCase;
import com.earth.server.user.domain.SignUpUseCase;
import com.earth.server.user.domain.UserRepository;
import com.earth.server.user.infra.persistence.JpaUserRepository;
import com.earth.server.user.infra.persistence.JpaUserRepositoryAdapter;
import com.earth.server.user.infra.web.JsonResponseMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeanConfig {
  @Bean
  UserRepository userRepository(JpaUserRepository jpaUserRepository) {
    return new JpaUserRepositoryAdapter(jpaUserRepository);
  }

  @Bean
  SignUpUseCase signUpUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return new SignUpUseCase(userRepository, passwordEncoder);
  }

  @Bean
  LoginUseCase loginUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return new LoginUseCase(userRepository, passwordEncoder);
  }

  @Bean
  JsonResponseMapper responseMapper(GenerateToken generateToken) {
    return new JsonResponseMapper(generateToken);
  }
}
