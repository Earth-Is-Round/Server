package com.earth.server.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SignUpUseCaseTest {
  private final UserRepository userRepository = mock(UserRepository.class);
  private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
  private final SignUpUseCase signUpUseCase = new SignUpUseCase(userRepository, passwordEncoder);

  private final Nickname defaultNickname = new Nickname("george");
  private final Password defaultPassword = new Password("george123");

  @Test
  @DisplayName("주어진 비밀번호를 암호화해서 유저를 회원가입 처리한다.")
  void run() {
    given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");

    signUpUseCase.run(defaultNickname, defaultPassword);

    var inOrder = inOrder(userRepository, passwordEncoder);
    inOrder.verify(passwordEncoder, times(1)).encode(anyString());
    inOrder.verify(userRepository, times(1)).add(anyString(), anyString());
  }
}