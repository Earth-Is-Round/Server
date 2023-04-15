package com.earth.server.user.infra.persistence;

import com.earth.server.user.domain.UserInfo;
import com.earth.server.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class JpaUserRepositoryAdapter implements UserRepository {
  private final JpaUserRepository jpaUserRepository;

  @Override
  public void add(String nickname, String encodedPassword){
    jpaUserRepository.save(new UserEntity(nickname, encodedPassword));
  }

  @Override
  public Optional<UserInfo> find(String nickname) {
    var optionalEntity = jpaUserRepository.findByNickname(nickname);
    if (optionalEntity.isPresent()) {
      var entity = optionalEntity.get();
      return Optional.of(new UserInfo(entity.getId(), entity.getNickname()));
    }

    return Optional.empty();
  }
}
