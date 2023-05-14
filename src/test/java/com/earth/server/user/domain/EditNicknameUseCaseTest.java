package com.earth.server.user.domain;

import com.earth.server.common.domain.DomainException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class EditNicknameUseCaseTest {
  private final UserRepository userRepository = mock(UserRepository.class);
  private final EditNicknameUseCase editNicknameUseCase = new EditNicknameUseCase(userRepository);

  private final UserId defaultUserId = new UserId(1L);
  private final Nickname defaultNickname = new Nickname("george");
  private final User defaultUser = new User(
    defaultUserId,
    "george",
    "encodedPassword"
  );

  @Test
  @DisplayName("닉네임이 중복된다면, DomainException 을 던진다")
  void duplicate_nickname() {
    given(userRepository.find(anyString())).willReturn(Optional.of(defaultUser));

    assertThrows(DomainException.class, () -> editNicknameUseCase.run(defaultUserId, defaultNickname));
  }

  @Test
  @DisplayName("닉네임이 중복되지 않았고, 주어진 닉네임으로 사용자를 변경한다.")
  void run() {
    given(userRepository.find(anyString())).willReturn(Optional.empty());

    editNicknameUseCase.run(defaultUserId, new Nickname("newNickname"));

    var inOrder = inOrder(userRepository);
    inOrder.verify(userRepository, times(1)).find(anyString());
    inOrder.verify(userRepository, times(1)).edit(any(UserId.class), any(Nickname.class));
  }
}