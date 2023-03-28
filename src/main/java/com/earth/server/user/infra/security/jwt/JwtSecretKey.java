package com.earth.server.user.infra.security.jwt;

import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;

import javax.crypto.SecretKey;

class JwtSecretKey {
  @Value("${jwt.secret}")
  private String key;

  public SecretKey get() {
    return Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
  }
}
