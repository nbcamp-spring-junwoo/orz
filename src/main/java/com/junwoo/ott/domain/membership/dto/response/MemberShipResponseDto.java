package com.junwoo.ott.domain.membership.dto.response;

import com.junwoo.ott.global.customenum.MembershipType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberShipResponseDto {

  private Long membershipId;
  private MembershipType membershipType;
  private Integer price;

}
