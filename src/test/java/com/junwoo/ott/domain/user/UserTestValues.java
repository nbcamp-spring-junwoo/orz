package com.junwoo.ott.domain.user;

import com.junwoo.ott.domain.auth.dto.body.AuthSignupDto;
import com.junwoo.ott.domain.auth.dto.reponse.AuthSignupRequestDto;
import com.junwoo.ott.domain.user.entity.User;
import java.time.LocalDate;

public interface UserTestValues {

  Long TEST_USER_ID = 1L;
  String TEST_USERNAME = "test1";
  String TEST_PASSWORD = "password";
  String ENCRYPT_PASSWORD = "encryptPassword";
  String TEST_EMAIL = "test1@email.com";
  String TEST_BORN = "2001-01-05";
  LocalDate TEST_DATE_BORN = LocalDate.parse(TEST_BORN);

  String TEST_USERNAME_ALREADY_EXIST_EXCEPTION_MESSAGE = "이미 존재하는 username입니다.";
  AuthSignupRequestDto TEST_AUTH_SIGNUP_REQUEST_DTO = new AuthSignupRequestDto(
      new AuthSignupDto(TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL, TEST_BORN)
  );

  User TEST_USER = User.builder()
      .userId(TEST_USER_ID)
      .username(TEST_USERNAME)
      .password(ENCRYPT_PASSWORD)
      .email(TEST_EMAIL)
      .born(TEST_DATE_BORN)
      .build();

}
