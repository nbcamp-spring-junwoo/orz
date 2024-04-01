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
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
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
  public void signup(@RequestBody @Valid AuthSignupDto authSignupDto) {
    authService.signup(new AuthSignupRequestDto(authSignupDto));
  }

  @PostMapping("/signup/admin")
  public void adminSignup(@RequestBody @Valid AuthAdminSignupDto authAdminSignupDto) {
    authService.adminSignup(new AuthAdminSignupRequestDto(authAdminSignupDto));
  }

  @PostMapping("/login")
  public void login(
      @RequestBody @Valid AuthLoginDto authLoginDto,
      HttpServletResponse response
  ) {
    // 사용자 인증
    AuthLoginRequestDto authLoginRequestDto = new AuthLoginRequestDto(authLoginDto);
    Authentication authentication = userService.login(authLoginRequestDto);

    // 토큰 생성 및 응답 설정
    // AccessToken -> 헤더, RefreshToken -> 쿠키
    setAuthenticationResponse(authentication, response);
  }

  @PostMapping("/login/admin")
  public void adminLogin(
      @RequestBody @Valid AuthLoginDto authLoginDto,
      HttpServletResponse response
  ) {
    // 사용자 인증
    AuthLoginRequestDto authLoginRequestDto = new AuthLoginRequestDto(authLoginDto);
    Authentication authentication = adminService.login(authLoginRequestDto);

    // 토큰 생성 및 응답 설정
    // AccessToken -> 헤더, RefreshToken -> 쿠키
    setAuthenticationResponse(authentication, response);
  }

  private void setAuthenticationResponse(
      Authentication authentication,
      HttpServletResponse response
  ) {
    String username = ((UserDetailsImpl) authentication.getPrincipal()).getUsername();
    // 토큰 생성
    String accessToken = jwtUtil.createAccessAndRefreshToken(username);

    // 헤더와 쿠키 설정
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, accessToken);
  }

}
