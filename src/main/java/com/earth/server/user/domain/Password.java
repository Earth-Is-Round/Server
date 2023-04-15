package com.earth.server.user.domain;

import com.earth.server.common.domain.DomainException;

import java.util.regex.Pattern;

import static com.earth.server.common.domain.ErrorCode.INVALID_PASSWORD;

public record Password(String value) {
  private static final Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{8,15}$");

  public Password {
    if (!pattern.matcher(value).matches()) {
      throw new DomainException(INVALID_PASSWORD);
    }
  }
}
