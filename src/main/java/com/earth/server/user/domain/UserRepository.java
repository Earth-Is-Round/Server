package com.earth.server.user.domain;

import java.util.Optional;

public interface UserRepository {
  void add(String nickname, String encodedPassword);
  Optional<UserInfo> find(String nickname);
}
