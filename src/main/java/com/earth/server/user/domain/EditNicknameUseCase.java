package com.earth.server.user.domain;

import com.earth.server.common.domain.DomainException;
import com.earth.server.common.domain.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
public class EditNicknameUseCase {
  private final UserRepository userRepository;

  @Transactional
  public void run(UserId id, Nickname nickname) {
    if (userRepository.find(nickname.value()).isPresent()) {
      throw new DomainException(ErrorCode.DUPLICATE_NICKNAME);
    }

    userRepository.edit(id, nickname);
  }
}
