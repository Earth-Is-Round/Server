package com.earth.server.user.domain;

import java.time.Instant;

public interface GenerateToken {
  String makeToken(Long id, Instant expiration);
}
