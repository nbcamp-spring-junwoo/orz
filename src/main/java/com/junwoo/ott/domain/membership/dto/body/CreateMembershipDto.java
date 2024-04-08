package com.junwoo.ott.domain.membership.dto.body;

import com.junwoo.ott.global.customenum.MembershipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CreateMembershipDto {

  private MembershipType membershipType;
  private Integer price;

}
