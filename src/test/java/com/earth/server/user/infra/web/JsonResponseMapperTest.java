package com.earth.server.user.infra.web;

import com.earth.server.user.domain.GenerateToken;
import com.earth.server.user.domain.Token;
import com.earth.server.user.domain.User;
import com.earth.server.user.domain.UserId;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

class JsonResponseMapperTest {
  private final GenerateToken generateToken = mock(GenerateToken.class);
  private final User defaultUser = new User(
    new UserId(1L),
    "george",
    "encodedPassword"
  );
  private final JsonResponseMapper mapper = new JsonResponseMapper(generateToken);

  @Test
  @DisplayName("User 를 JsonLoginResponse 로 매핑하여 반환한다.")
  void toLoginResponse() {
    given(generateToken.makeToken(anyLong())).willReturn(new Token("accessToken"));

    mapper.toLoginResponse(defaultUser);

    verify(generateToken, times(1)).makeToken(anyLong());
  }
}