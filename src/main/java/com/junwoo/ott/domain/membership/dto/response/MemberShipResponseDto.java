package com.junwoo.ott.domain.membership.dto.response;

import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.global.customenum.MembershipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberShipResponseDto {

  private Long membershipId;
  private MembershipType membershipType;
  private Long price;

  public Membership toEntity() {
    return Membership.builder()
        .membershipId(membershipId)
        .membershipType(membershipType)
        .price(price)
        .build();
  }

}
