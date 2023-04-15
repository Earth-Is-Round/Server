package com.earth.server.user.domain;

import com.earth.server.common.domain.DomainException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import static com.earth.server.common.domain.ErrorCode.NOT_EXIST_USER;
import static com.earth.server.common.domain.ErrorCode.NOT_MATCH_PASSWORD;

@RequiredArgsConstructor
public class LoginUseCase {
  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;

  public User run(Nickname nickname, Password password) {
    var user = userRepository.find(nickname.value()).orElseThrow(() -> new DomainException(NOT_EXIST_USER));

    if (!passwordEncoder.matches(password.value(), user.encodedPassword())) {
      throw new DomainException(NOT_MATCH_PASSWORD);
    }

    return user;
  }
}
