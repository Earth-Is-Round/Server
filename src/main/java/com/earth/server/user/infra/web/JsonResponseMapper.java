package com.earth.server.user.infra.web;

import com.earth.server.user.domain.GenerateToken;
import com.earth.server.user.domain.User;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class JsonResponseMapper {
  private final GenerateToken generateToken;

  JsonLoginResponse toLoginResponse(User user) {
    return new JsonLoginResponse(user.nickname(), generateToken.makeToken(user.id().value()).accessToken());
  }
}
