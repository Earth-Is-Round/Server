package com.earth.server.user.infra.web;

import com.earth.server.common.infra.JsonResponse;
import com.earth.server.user.domain.Nickname;
import com.earth.server.user.domain.Password;
import com.earth.server.user.domain.SignUpUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/users")
public class UserController {
  private final SignUpUseCase signUpUseCase;

  @PostMapping("/login")
  public ResponseEntity<?> login() {
    return JsonResponse.ok("login");
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody JsonSignUpRequest request) {
    signUpUseCase.run(new Nickname(request.nickname()), new Password(request.password()));

    return JsonResponse.noContent();
  }
}
