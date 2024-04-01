package com.junwoo.ott.domain.user;

import com.junwoo.ott.domain.auth.dto.body.AuthSignupDto;
import com.junwoo.ott.domain.auth.dto.request.AuthLoginRequestDto;
import com.junwoo.ott.domain.auth.dto.request.AuthSignupRequestDto;
import com.junwoo.ott.domain.user.dto.body.UserPutDto;
import com.junwoo.ott.domain.user.dto.request.UserPutRequestDto;
import com.junwoo.ott.domain.user.entity.User;
import java.time.LocalDate;

public interface UserTestValues {

  Long TEST_USER_ID = 1L;
  Long TEST_OTHER_USER_ID = 2L;
  String TEST_USERNAME = "test1";

  String TEST_USERNAME_STARTWITHS_ADMIN_PREFIX = "admin_prefix_username";
  String TEST_PASSWORD = "password";
  String TEST_NEW_PASSWORD = "newpassword";
  String TEST_ENCRYPT_PASSWORD = "encryptPassword";
  String TEST_NEW_ENCRYPT_PASSWORD = "newEncryptPassword";
  String TEST_EMAIL = "test1@email.com";
  String TEST_NEW_EMAIL = "newtest1@email.com";
  String TEST_BORN = "2001-01-05";
  String TEST_NEW_BORN = "2001-01-06";
  LocalDate TEST_DATE_BORN = LocalDate.parse(TEST_BORN);
  LocalDate TEST_NEW_DATE_BORN = LocalDate.parse(TEST_NEW_BORN);
  AuthSignupRequestDto TEST_AUTH_SIGNUP_REQUEST_DTO = new AuthSignupRequestDto(
      new AuthSignupDto(TEST_USERNAME, TEST_PASSWORD, TEST_EMAIL, TEST_BORN)
  );

  AuthSignupRequestDto TEST_AUTH_SIGNUP_REQUEST_DTO_STARTWITHS_ADMIN_PREFIX =
      new AuthSignupRequestDto(
          new AuthSignupDto(TEST_USERNAME_STARTWITHS_ADMIN_PREFIX, TEST_PASSWORD, TEST_EMAIL,
              TEST_BORN)
      );

  AuthLoginRequestDto TEST_AUTH_LOGIN_REQUEST_DTO = new AuthLoginRequestDto(
      TEST_USERNAME, TEST_PASSWORD
  );


  UserPutRequestDto TEST_USER_PUT_REQUEST_DTO = new UserPutRequestDto(
      TEST_USER_ID, TEST_USER_ID,
      new UserPutDto(TEST_PASSWORD, TEST_NEW_PASSWORD, TEST_NEW_EMAIL, TEST_NEW_BORN)
  );

  UserPutRequestDto TEST_USER_PUT_REQUEST_DTO_MISMATCH = new UserPutRequestDto(
      TEST_USER_ID, TEST_OTHER_USER_ID,
      new UserPutDto(TEST_PASSWORD, TEST_NEW_PASSWORD, TEST_NEW_EMAIL, TEST_NEW_BORN));

  User TEST_USER = User.builder()
      .userId(TEST_USER_ID)
      .username(TEST_USERNAME)
      .password(TEST_ENCRYPT_PASSWORD)
      .email(TEST_EMAIL)
      .born(TEST_DATE_BORN)
      .build();

}
