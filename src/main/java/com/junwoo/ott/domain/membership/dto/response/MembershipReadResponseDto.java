package com.junwoo.ott.domain.membership.dto.response;

import com.junwoo.ott.global.customenum.MembershipType;
import lombok.Getter;

@Getter
public class MembershipReadResponseDto {

  private final MembershipType membershipType;
  private final Integer price;
  private final String detail;

  public MembershipReadResponseDto(final MembershipType membershipType, final Integer price, final String detail) {
    this.membershipType = membershipType;
    this.price = price;
    this.detail = detail;
  }

}
