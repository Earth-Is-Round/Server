package com.earth.server.user.infra.security.jwt;

import com.earth.server.user.domain.UserId;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.StringUtils;

public class JwtResolver {
  private final JwtParser jwtParser;
  private static final String AUTHORIZATION_HEADER = "Authorization";
  private static final String TOKEN_PREFIX = "Bearer ";

  public JwtResolver(JwtSecretKey jwtSecretKey) {
    this.jwtParser = Jwts.parserBuilder().setSigningKey(jwtSecretKey.get()).build();
  }

  public Authentication getAuthentication(String accessToken) {
    Claims claims = jwtParser.parseClaimsJws(accessToken).getBody();

    UserId tempUserId = new UserId(Long.parseLong(claims.get("id").toString()));

    return new UsernamePasswordAuthenticationToken(tempUserId, null, null);
  }

  public boolean validateToken(String token) throws ExpiredJwtException, UnsupportedJwtException, MalformedJwtException, SignatureException, IllegalArgumentException {

    try {
      jwtParser.parseClaimsJws(token);
    } catch (ExpiredJwtException | UnsupportedJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
      return false;
    }
    return true;
  }

  public String resolveRequest(HttpServletRequest request) {
    String token = request.getHeader(AUTHORIZATION_HEADER);
    if (StringUtils.hasText(token) && token.startsWith(TOKEN_PREFIX)) {
      return token.substring(TOKEN_PREFIX.length());
    }

    return null;
  }
}
