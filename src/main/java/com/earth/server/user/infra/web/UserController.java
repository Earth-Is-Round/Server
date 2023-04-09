package com.earth.server.user.infra.web;

import com.earth.server.common.infra.JsonResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @PostMapping("/login")
  public ResponseEntity<?> login() {
    return JsonResponse.ok("login");
  }

  @PostMapping("/signup")
  public ResponseEntity<?> signup() {
    return JsonResponse.noContent();
  }
}
