package com.earth.server.user.infra.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

  @PostMapping("/login")
  public ResponseEntity<String> login() {
    return ResponseEntity.ok("login");
  }

  @PostMapping("/signup")
  public ResponseEntity<String> signup() {
    return ResponseEntity.ok("sing up");
  }
}
