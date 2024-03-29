package com.junwoo.ott.domain.coupon.test;

import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.customenum.AuthorityType;
import com.junwoo.ott.global.customenum.MembershipType;
import java.time.LocalDate;

public interface CouponUserTestValues {

  Long TEST_USER_ID = 1L;
  String TEST_USER_NAME = "TEST";
  String TEST_USER_PASSWORD = "TEST_PASSWORD_123";
  String TEST_EMAIL = "Test123@naver.com";
  LocalDate TEST_BORN = LocalDate.of(1997, 3, 15);
  AuthorityType TEST_ADMIN_TYPE = AuthorityType.ADMIN;
  AuthorityType TEST_USER_TYPE = AuthorityType.USER;
  MembershipType TEST_MEMBERSHIP = MembershipType.SILVER;

  User TEST_USER = User.builder()
      .userId(TEST_USER_ID)
      .username(TEST_USER_NAME)
      .password(TEST_USER_PASSWORD)
      .email(TEST_EMAIL)
      .born(TEST_BORN)
      .authorityType(TEST_ADMIN_TYPE)
      .membershipType(TEST_MEMBERSHIP)
      .build();

}
