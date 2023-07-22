package com.earth.server.user.domain;

import com.earth.server.user.infra.persistence.UserEntity;

import java.util.Optional;

public interface UserRepository {
  void add(String nickname, String encodedPassword);
  Optional<User> find(String nickname);
  Optional<UserEntity> find(Long id);
  void edit(UserId id, Nickname nickname);
  void edit(UserId id, String encodedPassword);
}
