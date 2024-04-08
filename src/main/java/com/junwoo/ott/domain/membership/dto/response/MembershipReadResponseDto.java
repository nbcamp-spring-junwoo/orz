package com.junwoo.ott.domain.membership.dto.response;

import com.junwoo.ott.global.customenum.MembershipType;
import lombok.Getter;

@Getter
public class MembershipReadResponseDto {

  private final MembershipType membershipType;
  private final Integer price;

  public MembershipReadResponseDto(MembershipType membershipType, Integer price) {
    this.membershipType = membershipType;
    this.price = price;
  }

}
