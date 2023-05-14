package com.earth.server.user.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class EditPasswordUseCaseTest {
  private final UserRepository userRepository = mock(UserRepository.class);
  private final PasswordEncoder passwordEncoder = mock(PasswordEncoder.class);
  private final EditPasswordUseCase editPasswordUseCase = new EditPasswordUseCase(userRepository, passwordEncoder);

  private final UserId defaultUserId = new UserId(1L);
  private final Password defaultPassword = new Password("george123");

  @Test
  @DisplayName("주어진 비밀번호로 사용자를 변경한다.")
  void run() {
    given(passwordEncoder.encode(anyString())).willReturn("encodedPassword");

    editPasswordUseCase.run(defaultUserId, defaultPassword);

    var inOrder = inOrder(userRepository, passwordEncoder);
    inOrder.verify(passwordEncoder, times(1)).encode(anyString());
    inOrder.verify(userRepository, times(1)).edit(any(UserId.class), anyString());
  }
}