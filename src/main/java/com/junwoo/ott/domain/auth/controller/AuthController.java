package com.junwoo.ott.domain.auth.controller;

import com.junwoo.ott.domain.auth.dto.body.AuthSignupDto;
import com.junwoo.ott.domain.auth.dto.reponse.AuthSignupRequestDto;
import com.junwoo.ott.domain.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1")
@RestController
public class AuthController {

  private final AuthService authService;

  @PostMapping("/signup")
  public void signup(@RequestBody AuthSignupDto authSignupDto) {
    authService.signup(new AuthSignupRequestDto(authSignupDto));
  }
}
