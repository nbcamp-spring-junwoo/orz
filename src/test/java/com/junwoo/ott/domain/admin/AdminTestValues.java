package com.junwoo.ott.domain.admin;

import com.junwoo.ott.domain.admin.entity.Admin;
import com.junwoo.ott.domain.auth.dto.request.AuthAdminSignupRequestDto;
import com.junwoo.ott.global.customenum.AuthorityType;

public interface AdminTestValues {

  Long TEST_ADMIN_ID = 1L;
  String TEST_ADMIN_USERNAME = "admin";
  String TEST_PASSWORD = "admin";
  String TEST_ENCRYPT_PASSWORD = "encryptPassword";
  String TEST_ADMIN_KEY = "admin_key";
  String TEST_INVALID_ADMIN_KEY = "invalid_admin_key";
  String TEST_ADMIN_PREFIX = "admin_prefix";

  AuthAdminSignupRequestDto TEST_AUTH_ADMIN_SIGNUP_REQUEST_DTO = new AuthAdminSignupRequestDto(
      TEST_ADMIN_USERNAME, TEST_PASSWORD, TEST_ADMIN_KEY);

  AuthAdminSignupRequestDto TEST_AUTH_ADMIN_SIGNUP_REQUEST_DTO_ADMIN_KEY_INVALID =
      new AuthAdminSignupRequestDto(TEST_ADMIN_USERNAME, TEST_PASSWORD, TEST_INVALID_ADMIN_KEY);

  Admin TEST_ADMIN = Admin.builder()
      .adminId(TEST_ADMIN_ID)
      .username(TEST_ADMIN_PREFIX + TEST_ADMIN_USERNAME)
      .password(TEST_ENCRYPT_PASSWORD)
      .authorityType(AuthorityType.ROLE_ADMIN)
      .build();

}
