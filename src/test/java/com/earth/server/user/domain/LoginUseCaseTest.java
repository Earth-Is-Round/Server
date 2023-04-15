package com.earth.server.user.domain;

import com.earth.server.common.domain.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;

class LoginUseCaseTest {
  private final UserRepository userRepository = mock(UserRepository.class);
  private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
  private final LoginUseCase loginUseCase = new LoginUseCase(userRepository, passwordEncoder);

  private final Nickname defaultNickname = new Nickname("george");
  private final Password defaultPassword = new Password("george123");
  private final User defaultUser = new User(
    new UserId(1L),
    "george",
    "encodedPassword"
  );

  @Test
  @DisplayName("nickname 에 해당하는 유저가 없으면, DomainException 을 던진다.")
  void not_exist_user() {
    given(userRepository.find(anyString())).willReturn(Optional.empty());

    assertThrows(DomainException.class, () -> loginUseCase.run(defaultNickname, defaultPassword));
  }

  @Test
  @DisplayName("입력한 password 가 일치하지 않으면, DomainException 을 던진다.")
  void not_match_password() {
    given(userRepository.find(anyString())).willReturn(Optional.of(defaultUser));
    given(passwordEncoder.matches(anyString(), anyString())).willReturn(false);

    assertThrows(DomainException.class, () -> loginUseCase.run(defaultNickname, defaultPassword));
  }

  @Test
  @DisplayName("닉네임에 해당하는 유저를 찾고, 비밀번호를 비교한 뒤, 유저를 반환한다.")
  void run() {
    given(userRepository.find(anyString())).willReturn(Optional.of(defaultUser));
    given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

    var loginUser = loginUseCase.run(defaultNickname, defaultPassword);

    var inOrder = Mockito.inOrder(userRepository, passwordEncoder);
    inOrder.verify(userRepository, times(1)).find(anyString());
    inOrder.verify(passwordEncoder, times(1)).matches(anyString(), anyString());
    assertEquals(defaultUser, loginUser);
  }
}