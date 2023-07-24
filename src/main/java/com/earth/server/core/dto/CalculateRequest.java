package com.earth.server.core.dto;

import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CalculateRequest(
        @NotNull List<Calculating> calculatingList
) {
}
