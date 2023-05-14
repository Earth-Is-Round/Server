package com.earth.server.user.domain;

import com.earth.server.common.domain.DomainException;
import com.earth.server.common.domain.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class SignUpUseCase {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  @Transactional
  public void run(Nickname nickname, Password password) {
    if (userRepository.find(nickname.value()).isPresent()) {
      throw new DomainException(ErrorCode.DUPLICATE_NICKNAME);
    }

    userRepository.add(nickname.value(), passwordEncoder.encode(password.value()));
  }
}
