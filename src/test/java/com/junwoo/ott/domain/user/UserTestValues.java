package com.junwoo.ott.domain.user;

import com.junwoo.ott.domain.auth.dto.body.AuthSignupDto;
import com.junwoo.ott.domain.auth.dto.reponse.AuthSignupRequestDto;

public interface UserTestValues {

  Long TEST_USER_ID = 1L;
  String TEST_USERNAME = "test1";
  String TEST_PASSWORD = "password";
  String ENCRYPT_PASSWORD = "encryptPassword";
  String TEST_EMAIL = "test1@email.com";
  String TEST_BORN = "2001-01-05";

  String TEST_USERNAME_ALREADY_EXIST_EXCEPTION_MESSAGE = "이미 존재하는 username입니다.";
  AuthSignupRequestDto TEST_AUTH_SIGNUP_REQUEST_DTO = new AuthSignupRequestDto(
      new AuthSignupDto("test1", "password", "test1@mail.com", "2001-01-05")
  );

}
