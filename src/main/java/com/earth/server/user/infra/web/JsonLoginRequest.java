package com.earth.server.user.infra.web;

public record JsonLoginRequest(String nickname, String password) {
}
