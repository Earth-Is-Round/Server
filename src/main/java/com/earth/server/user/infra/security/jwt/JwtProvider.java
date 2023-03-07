package com.earth.server.user.infra.security.jwt;

import com.earth.server.user.domain.GenerateToken;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.sql.Date;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtProvider implements GenerateToken {
  private final JwtSecretKey secretKey;

  @Override
  public String makeToken(Long id, Instant expiration) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", id);
    return Jwts.builder()
      .setClaims(claims)
      .setExpiration(Date.from(expiration))
      .signWith(secretKey.get())
      .compact();
  }
}
