package com.earth.server.core.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SnowballSize {
    BIG(3),
    NORMAL(2),
    SMALL(1),
    ;
    private final int value;
}
