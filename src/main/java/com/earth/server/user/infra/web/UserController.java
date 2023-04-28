package com.earth.server.user.infra.web;

import com.earth.server.common.infra.JsonResponse;
import com.earth.server.user.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/users")
public class UserController {
  private final SignUpUseCase signUpUseCase;
  private final LoginUseCase loginUseCase;
  private final JsonResponseMapper mapper;

  @PostMapping("/login")
  public ResponseEntity<?> login(@RequestBody JsonLoginRequest request) {
    var loginUser = loginUseCase.run(new Nickname(request.nickname()), new Password(request.password()));

    return JsonResponse.ok(mapper.toLoginResponse(loginUser));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@RequestBody JsonSignUpRequest request) {
    signUpUseCase.run(new Nickname(request.nickname()), new Password(request.password()));

    return JsonResponse.created();
  }

  // TODO: test 용 api, 추후 지우기
  @GetMapping("/test")
  public ResponseEntity<?> test(@Auth UserId id){
    return JsonResponse.ok(id);
  }
}
