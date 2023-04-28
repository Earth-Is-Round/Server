package com.earth.server.user.infra.web;

import jakarta.validation.constraints.NotNull;

public record JsonSignUpRequest(@NotNull String nickname, @NotNull String password) {
}
