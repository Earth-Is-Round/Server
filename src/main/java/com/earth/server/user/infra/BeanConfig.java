package com.earth.server.user.infra;

import com.earth.server.user.domain.*;
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
  EditNicknameUseCase editNicknameUseCase(UserRepository userRepository) {
    return new EditNicknameUseCase(userRepository);
  }

  @Bean
  EditPasswordUseCase editPasswordUseCase(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    return new EditPasswordUseCase(userRepository, passwordEncoder);
  }

  @Bean
  JsonResponseMapper responseMapper(GenerateToken generateToken) {
    return new JsonResponseMapper(generateToken);
  }
}
