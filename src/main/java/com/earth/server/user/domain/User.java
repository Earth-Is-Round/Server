package com.earth.server.user.domain;


public record User(UserId id, String nickname, String encodedPassword) {
}
