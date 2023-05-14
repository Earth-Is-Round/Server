package com.earth.server.user.infra.web;

import jakarta.validation.constraints.NotNull;

public record JsonEditPasswordRequest(@NotNull String password) {
}
