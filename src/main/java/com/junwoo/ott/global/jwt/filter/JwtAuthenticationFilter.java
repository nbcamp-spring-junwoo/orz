package com.junwoo.ott.global.jwt.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.junwoo.ott.domain.auth.dto.body.AuthLoginDto;
import com.junwoo.ott.global.exception.custom.LoginFailException;
import com.junwoo.ott.global.jwt.JwtUtil;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j(topic = "로그인 및 JWT 생성")
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

  private final JwtUtil jwtUtil;

  public JwtAuthenticationFilter(JwtUtil jwtUtil) {
    this.jwtUtil = jwtUtil;
    setFilterProcessesUrl("/api/v1/login");
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request, HttpServletResponse response
  ) throws AuthenticationException {
    try {
      AuthLoginDto authLoginDto = new ObjectMapper().readValue(request.getInputStream(),
          AuthLoginDto.class);

      return getAuthenticationManager().authenticate(
          new UsernamePasswordAuthenticationToken(authLoginDto.getUsername(),
              authLoginDto.getPassword()));

    } catch (IOException e) {
      log.error(e.getMessage());
      throw new RuntimeException(e.getMessage());
    }
  }

  @Override
  protected void successfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain,
      Authentication authResult
  ) {
    UserDetailsImpl user = (UserDetailsImpl) authResult.getPrincipal();

    String token = jwtUtil.createAccessAndRefreshToken(user.getUsername());
    response.addHeader(JwtUtil.AUTHORIZATION_HEADER, token);

  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request, HttpServletResponse response, AuthenticationException failed
  ) {
    throw new LoginFailException("로그인에 실패하였습니다.");
  }

}
