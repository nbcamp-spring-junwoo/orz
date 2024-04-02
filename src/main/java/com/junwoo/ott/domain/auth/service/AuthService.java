package com.junwoo.ott.domain.auth.service;

import com.junwoo.ott.domain.admin.service.AdminService;
import com.junwoo.ott.domain.auth.dto.request.AuthAdminSignupRequestDto;
import com.junwoo.ott.domain.auth.dto.request.AuthSignupRequestDto;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.exception.custom.AdminKeyNotCorrectException;
import com.junwoo.ott.global.exception.custom.UsernameHasAdminPreFixException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AuthService {

  private final UserService userService;
  private final AdminService adminService;

  @Value("${admin.key}")
  private String adminKey;
  @Value("${admin.prefix}")
  private String adminPrefix;

  public void signup(final AuthSignupRequestDto authSignupRequestDto) {

    String username = authSignupRequestDto.getUsername();
    validateUsernameStartWith(username);
    userService.validateUserNotExist(username);
    userService.createUser(authSignupRequestDto);
  }

  public void adminSignup(final AuthAdminSignupRequestDto authAdminSignupRequestDto) {

    validateCorrectAdminKey(authAdminSignupRequestDto.getAdminKey());
    adminService.validateAdminNotExist(authAdminSignupRequestDto.getUsername());
    adminService.createAdmin(authAdminSignupRequestDto);
  }

  private void validateCorrectAdminKey(final String adminKey) {

    if (!this.adminKey.equals(adminKey)) {
      throw new AdminKeyNotCorrectException("관리자 키가 일치하지 않습니다.");
    }
  }

  private void validateUsernameStartWith(final String username) {

    if (username.startsWith(adminPrefix)) {
      throw new UsernameHasAdminPreFixException("username에 admin prefix 포함");
    }
  }

}
