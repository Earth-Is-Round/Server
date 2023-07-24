package com.earth.server.user.infra.web;

import com.earth.server.common.domain.DomainException;
import com.earth.server.common.infra.JsonResponse;
import com.earth.server.user.domain.*;
import com.earth.server.user.infra.persistence.JpaUserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.earth.server.common.domain.ErrorCode.NOT_EXIST_USER;

@RestController
@RequiredArgsConstructor
@RequestMapping("/apis/users")
public class UserController {
  private final SignUpUseCase signUpUseCase;
  private final LoginUseCase loginUseCase;
  private final EditNicknameUseCase editNicknameUseCase;
  private final EditPasswordUseCase editPasswordUseCase;
  private final JpaUserRepository jpaUserRepository;
  private final JsonResponseMapper mapper;

  @PostMapping("/login")
  public ResponseEntity<?> login(@Valid @RequestBody JsonLoginRequest request) {
    var loginUser = loginUseCase.run(new Nickname(request.nickname()), new Password(request.password()));

    return JsonResponse.ok(mapper.toLoginResponse(loginUser));
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signUp(@Valid @RequestBody JsonSignUpRequest request) {
    signUpUseCase.run(new Nickname(request.nickname()), new Password(request.password()));

    return JsonResponse.created();
  }

  @PatchMapping
  public ResponseEntity<?> editNickname(
    @RequestParam("nickname") String nickname,
    @Auth UserId userId
  ) {
    editNicknameUseCase.run(userId, new Nickname(nickname));
    return JsonResponse.okWithNoData();
  }

  @PatchMapping("/password")
  public ResponseEntity<?> editPassword(
    @Valid @RequestBody JsonEditPasswordRequest request,
    @Auth UserId userId
  ) {
    editPasswordUseCase.run(userId, new Password(request.password()));
    return JsonResponse.okWithNoData();
  }

  @DeleteMapping
  public ResponseEntity<?> deleteUser(@Auth UserId id) {
    jpaUserRepository.delete(jpaUserRepository.findById(id.value()).orElseThrow(() -> new DomainException(NOT_EXIST_USER)));
    return JsonResponse.okWithNoData();
  }

  // TODO: test 용 api, 추후 지우기
  @GetMapping("/test")
  public ResponseEntity<?> test(@Auth UserId id) {
    return JsonResponse.ok(id);
  }
}
