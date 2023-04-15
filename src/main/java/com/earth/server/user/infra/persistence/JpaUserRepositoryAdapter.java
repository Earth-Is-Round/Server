package com.earth.server.user.infra.persistence;

import com.earth.server.user.domain.User;
import com.earth.server.user.domain.UserId;
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
  public Optional<User> find(String nickname) {
    var optionalEntity = jpaUserRepository.findByNickname(nickname);
    if (optionalEntity.isPresent()) {
      var entity = optionalEntity.get();
      return Optional.of(new User(new UserId(entity.getId()), entity.getNickname(), entity.getPassword()));
    }

    return Optional.empty();
  }
}
