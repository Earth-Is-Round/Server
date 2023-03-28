package com.earth.server.user.domain;

public interface GenerateToken {
  Token makeToken(Long id);
}
