package com.junwoo.ott.domain.auth.service;

import com.junwoo.ott.domain.auth.dto.reponse.AuthSignupRequestDto;
import com.junwoo.ott.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

  private final UserService userService;

  public void signup(AuthSignupRequestDto authSignupRequestDto) {
    userService.validateUserNotExist(authSignupRequestDto.getUsername());
    userService.createUser(authSignupRequestDto);
  }

}
