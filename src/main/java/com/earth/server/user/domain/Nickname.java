package com.earth.server.user.domain;

import com.earth.server.common.domain.DomainException;

import java.util.regex.Pattern;

import static com.earth.server.common.domain.ErrorCode.INVALID_NICKNAME;

public record Nickname(String value) {
  private static final Pattern pattern = Pattern.compile("^[a-zA-Z]{4,12}$");

  public Nickname {
    if (!pattern.matcher(value).matches()) {
      throw new DomainException(INVALID_NICKNAME);
    }
  }
}
