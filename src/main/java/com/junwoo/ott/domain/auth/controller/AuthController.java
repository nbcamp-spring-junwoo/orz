package com.junwoo.ott.domain.auth.controller;

import com.junwoo.ott.domain.auth.dto.body.AuthAdminSignupDto;
import com.junwoo.ott.domain.auth.dto.body.AuthSignupDto;
import com.junwoo.ott.domain.auth.dto.request.AuthAdminSignupRequestDto;
import com.junwoo.ott.domain.auth.dto.request.AuthSignupRequestDto;
import com.junwoo.ott.domain.auth.service.AuthService;
import jakarta.validation.Valid;
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
  public void signup(@RequestBody @Valid AuthSignupDto authSignupDto) {
    authService.signup(new AuthSignupRequestDto(authSignupDto));
  }

  @PostMapping("/signup/admin")
  public void adminSignup(@RequestBody @Valid AuthAdminSignupDto authAdminSignupDto) {
    authService.adminSignup(new AuthAdminSignupRequestDto(authAdminSignupDto));
  }

}
