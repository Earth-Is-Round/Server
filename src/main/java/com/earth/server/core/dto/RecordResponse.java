package com.earth.server.core.dto;

import java.util.List;

public record RecordResponse(
        List<Item> items,
        List<Integer> steps,
        Snowman snowman) {
}
