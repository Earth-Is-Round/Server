package com.earth.server.user.domain;

import com.earth.server.common.domain.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class SignUpUseCaseTest {
  private final UserRepository userRepository = mock(UserRepository.class);
  private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
  private final SignUpUseCase signUpUseCase = new SignUpUseCase(userRepository, passwordEncoder);

  private final Nickname defaultNickname = new Nickname("george");
  private final Password defaultPassword = new Password("george123");
  private final User defaultUser = new User(
    new UserId(1L),
    "george",
    "encodedPassword"
  );

  @Test
  @DisplayName("닉네임이 중복된다면, DomainException 을 던진다")
  void duplicate_nickname() {
    given(userRepository.find(anyString())).willReturn(Optional.of(defaultUser));

    assertThrows(DomainException.class, () -> signUpUseCase.run(defaultNickname, defaultPassword));
  }

  @Test
  @DisplayName("닉네임이 중복되지 않았다면, 주어진 비밀번호를 암호화해서 유저를 회원가입 처리한다.")
  void run() {
    given(userRepository.find(anyString())).willReturn(Optional.empty());
    given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");

    signUpUseCase.run(defaultNickname, defaultPassword);

    var inOrder = inOrder(userRepository, passwordEncoder);
    inOrder.verify(userRepository, times(1)).find(anyString());
    inOrder.verify(passwordEncoder, times(1)).encode(anyString());
    inOrder.verify(userRepository, times(1)).add(anyString(), anyString());
  }
}