package com.junwoo.ott.domain.membership.dto.request;

import com.junwoo.ott.domain.membership.dto.body.CreateMembershipDto;
import com.junwoo.ott.global.customenum.AuthorityType;
import com.junwoo.ott.global.customenum.MembershipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class MembershipCreateRequestDto {

  private AuthorityType authority;
  private MembershipType membershipType;
  private Integer price;

  public MembershipCreateRequestDto(
      AuthorityType authorityType,
      CreateMembershipDto createMembershipDto
  ) {
    this.authority = authorityType;
    this.membershipType = createMembershipDto.getMembershipType();
    this.price = createMembershipDto.getPrice();
  }

}
