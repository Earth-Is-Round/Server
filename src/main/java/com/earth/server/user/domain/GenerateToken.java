package com.earth.server.user.domain;

import java.time.Instant;

public interface GenerateToken {
  Token makeToken(Long id, Instant expiration);
}
