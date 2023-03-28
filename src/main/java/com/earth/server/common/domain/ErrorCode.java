package com.earth.server.common.domain;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum ErrorCode {
  DUPLICATE_ID("Duplicate Login Id"),
  UNEXPECTED_SERVER_ERROR("Unexpected Server Error");

  private final String description;
}
