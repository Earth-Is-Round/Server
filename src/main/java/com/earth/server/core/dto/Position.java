package com.earth.server.core.dto;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum Position {
    TOP(null),
    BOTTOM(null),

    // TOP
    MOUTH(TOP),
    EYES(TOP),
    NOSE(TOP),
    NECK(TOP),
    HEAD(TOP),
    EAR(TOP),

    // BOTTOM
    BODY(BOTTOM),
    FOOT(BOTTOM),
    HAND(BOTTOM),
    ;

    private final Position parents;

    public boolean wearable() {
        return this.parents != null;
    }
}
