package com.earth.server.common.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DomainException extends RuntimeException {
  private final ErrorCode code;

  @Override
  public String getMessage() {
    return code.name();
  }
}
