package com.junwoo.ott.domain.membership.test;

import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.global.customenum.MembershipType;

public interface MembershipServiceTestValues {

  Membership TEST_MEMBERSHIP = Membership.builder()
      .membershipId(1L)
      .membershipType(MembershipType.ROLE_NORMAL)
      .price(10000)
      .build();

}
