package com.junwoo.ott.domain.auth.controller;

import com.junwoo.ott.domain.admin.service.AdminService;
import com.junwoo.ott.domain.auth.dto.body.AuthAdminSignupDto;
import com.junwoo.ott.domain.auth.dto.body.AuthLoginDto;
import com.junwoo.ott.domain.auth.dto.body.AuthSignupDto;
import com.junwoo.ott.domain.auth.dto.request.AuthAdminSignupRequestDto;
import com.junwoo.ott.domain.auth.dto.request.AuthLoginRequestDto;
import com.junwoo.ott.domain.auth.dto.request.AuthSignupRequestDto;
import com.junwoo.ott.domain.auth.service.AuthService;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.jwt.JwtUtil;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1")
@RestController
public class AuthController {

  private final AuthService authService;
  private final UserService userService;
  private final AdminService adminService;

  private final JwtUtil jwtUtil;

  @PostMapping("/signup")
  public void signup(@RequestBody @Validated final AuthSignupDto authSignupDto) {
    authService.signup(new AuthSignupRequestDto(authSignupDto));
  }

  @PostMapping("/admin/signup")
  public void adminSignup(@RequestBody @Validated final AuthAdminSignupDto authAdminSignupDto) {
    authService.adminSignup(new AuthAdminSignupRequestDto(authAdminSignupDto));
  }

  @PostMapping("/login")
  public void login(
      @RequestBody @Validated final AuthLoginDto authLoginDto,
      final HttpServletResponse response
  ) {
    AuthLoginRequestDto authLoginRequestDto = new AuthLoginRequestDto(authLoginDto);
    Authentication authentication = userService.login(authLoginRequestDto);

    setAuthenticationResponse(authentication, response);
  }

  @PostMapping("/admin/login")
  public void adminLogin(
      @RequestBody @Validated final AuthLoginDto authLoginDto,
      final HttpServletResponse response
  ) {
    AuthLoginRequestDto authLoginRequestDto = new AuthLoginRequestDto(authLoginDto);
    Authentication authentication = adminService.login(authLoginRequestDto);

    setAuthenticationResponse(authentication, response);
  }

  private void setAuthenticationResponse(
      final Authentication authentication,
      final HttpServletResponse response
  ) {
    String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
    String accessToken = jwtUtil.createAccessAndRefreshToken(username);

    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
  }

}
