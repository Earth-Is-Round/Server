package com.earth.server.user.infra.security.jwt;

import com.earth.server.user.domain.GenerateToken;
import com.earth.server.user.domain.Token;
import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.time.Period;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiredArgsConstructor
public class JwtProvider implements GenerateToken {
  private final JwtSecretKey secretKey;
  private static final Period EXPIRATION_PERIOD = Period.ofDays(60);

  @Override
  public Token makeToken(Long id) {
    Map<String, Object> claims = new HashMap<>();
    claims.put("id", id);
    var accessToken = Jwts.builder()
      .setClaims(claims)
      .setExpiration(Date.from(Instant.now().plus(EXPIRATION_PERIOD)))
      .signWith(secretKey.get())
      .compact();

    return new Token(accessToken);
  }
}
