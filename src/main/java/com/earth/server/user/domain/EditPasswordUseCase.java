package com.earth.server.user.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class EditPasswordUseCase {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void run(UserId id, Password password) {
    userRepository.edit(id, passwordEncoder.encode(password.value()));
  }
}
