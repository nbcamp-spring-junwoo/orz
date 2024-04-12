package com.junwoo.ott.domain.coupon.test;

import com.junwoo.ott.domain.user.dto.response.UserReadResponseDto;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.customenum.AuthorityType;
import com.junwoo.ott.global.customenum.MembershipType;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface CouponUserTestValues {

  Long TEST_USER_ID = 1L;
  String TEST_USER_NAME = "TEST";
  String TEST_USER_PASSWORD = "TEST_PASSWORD_123";
  String TEST_EMAIL = "Test123@naver.com";
  LocalDate TEST_BORN = LocalDate.of(1997, 3, 15);
  AuthorityType TEST_ADMIN_TYPE = AuthorityType.ROLE_ADMIN;
  AuthorityType TEST_USER_TYPE = AuthorityType.ROLE_USER;
  MembershipType TEST_MEMBERSHIP = MembershipType.ROLE_SILVER;
  LocalDateTime TEST_CREATED_AT = LocalDateTime.parse("2024-03-01T00:00:00");

  User TEST_USER = User.builder()
      .userId(TEST_USER_ID)
      .username(TEST_USER_NAME)
      .password(TEST_USER_PASSWORD)
      .email(TEST_EMAIL)
      .born(TEST_BORN)
      .authorityType(TEST_ADMIN_TYPE)
      .membershipType(TEST_MEMBERSHIP)
      .build();

  UserReadResponseDto TEST_USER_READ_RESPONSE_DTO = new UserReadResponseDto(
      TEST_USER_ID,
      TEST_USER_NAME,
      TEST_EMAIL,
      TEST_BORN,
      TEST_USER_TYPE,
      TEST_MEMBERSHIP,
      TEST_CREATED_AT,
      TEST_CREATED_AT
  );

}
