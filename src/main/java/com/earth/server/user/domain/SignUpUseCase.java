package com.earth.server.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

@RequiredArgsConstructor
public class SignUpUseCase {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public void run(Nickname nickname, Password password) {
    userRepository.add(nickname.value(), passwordEncoder.encode(password.value()));
  }
}
