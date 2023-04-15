package com.earth.server.user.infra.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaUserRepository extends JpaRepository<UserEntity, Long> {
  Optional<UserEntity> findByNickname(String nickname);
}
