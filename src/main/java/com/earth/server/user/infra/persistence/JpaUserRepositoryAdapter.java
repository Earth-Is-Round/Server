package com.earth.server.user.infra.persistence;

import com.earth.server.common.domain.DomainException;
import com.earth.server.user.domain.Nickname;
import com.earth.server.user.domain.User;
import com.earth.server.user.domain.UserId;
import com.earth.server.user.domain.UserRepository;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.earth.server.common.domain.ErrorCode.NOT_EXIST_USER;

// TODO: 추후 JPA 테스트 추가해도 될 듯
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

  @Override
  public void edit(UserId id, Nickname nickname) {
    var userEntity = jpaUserRepository.findById(id.value()).orElseThrow(() -> new DomainException(NOT_EXIST_USER));

    userEntity.setNickname(nickname.value());
  }

  @Override
  public void edit(UserId id, String encodedPassword) {
    var userEntity = jpaUserRepository.findById(id.value()).orElseThrow(() -> new DomainException(NOT_EXIST_USER));

    userEntity.setPassword(encodedPassword);
  }
}
