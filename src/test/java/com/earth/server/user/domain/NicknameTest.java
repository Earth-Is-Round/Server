package com.earth.server.user.domain;

import com.earth.server.common.domain.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class NicknameTest {
  @Test
  void 닉네임은_영어_4글자_이상_12글자_이하() {
    var nickname = new Nickname("george");

    assertEquals("george", nickname.value());
  }

  @Test
  void 닉네임이_4글자_미만이면_Exception() {
    assertThrows(DomainException.class, () -> new Nickname("abc"));
  }

  @Test
  void 닉네임이_12글자_초과면_Exception() {
    assertThrows(DomainException.class, () -> new Nickname("abcabcabcabcabc"));
  }

  @Test
  void 닉네임이_영어_외_다른_글자면_Exception() {
    assertThrows(DomainException.class, () -> new Nickname("abc123"));
  }
}