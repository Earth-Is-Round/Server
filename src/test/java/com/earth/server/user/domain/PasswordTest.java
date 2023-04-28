package com.earth.server.user.domain;

import com.earth.server.common.domain.DomainException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PasswordTest {
  @Test
  void 비밀번호는_영어_숫자_8글자_이상_15글자_이하() {
    var password = new Password("george123");

    assertEquals("george123", password.value());
  }

  @Test
  void 비밀번호가_8글자_미만이면_Exception() {
    assertThrows(DomainException.class, () -> new Password("abcabca"));
  }

  @Test
  void 비밀번호가_15글자_초과면_Exception() {
    assertThrows(DomainException.class, () -> new Password("abcabcabcabcabca"));
  }

  @Test
  void 비밀번호가_영어_숫자_외_다른_글자면_Exception() {
    assertThrows(DomainException.class, () -> new Nickname("abcd1234*"));
  }
}
