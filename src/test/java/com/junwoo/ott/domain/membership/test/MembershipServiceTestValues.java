package com.junwoo.ott.domain.membership.test;

import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.global.customenum.MembershipType;

public interface MembershipServiceTestValues {

  Membership TEST_MEMBERSHIP = Membership.builder()
      .membershipId(1L)
      .membershipType(MembershipType.ROLE_NORMAL)
      .price(10000)
      .build();


  Membership TEST_NORMAL_MEMBERSHIP_TYPE = Membership
      .builder()
      .membershipId(1L)
      .membershipType(MembershipType.ROLE_NORMAL)
      .price(0)
      .detail("혜택을 이용하려면 구독신청")
      .build();

  Membership TEST_BRONZE_MEMBERSHIP_TYPE = Membership
      .builder()
      .membershipId(2L)
      .membershipType(MembershipType.ROLE_BRONZE)
      .price(5000)
      .detail(
          "광고형, 일부 소수의 영화 및 시리즈를 제외한 모든 콘텐츠 이용 가능#지원되는 디바이스에서 동시접속 2명까지 시청 가능#풀 HD로 시청 가능#한 번에 2대의 지원되는 디바이스에서 콘텐츠 저장 가능")
      .build();

  Membership TEST_SILVER_MEMBERSHIP_TYPE = Membership
      .builder()
      .membershipId(3L)
      .membershipType(MembershipType.ROLE_SILVER)
      .price(10000)
      .build();

  Membership TEST_GOLD_MEMBERSHIP_TYPE = Membership
      .builder()
      .membershipId(4L)
      .membershipType(MembershipType.ROLE_GOLD)
      .price(15000)
      .build();

}
