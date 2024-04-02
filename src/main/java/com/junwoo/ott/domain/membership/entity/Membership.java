package com.junwoo.ott.domain.membership.entity;

import com.junwoo.ott.domain.membership.dto.response.MemberShipResponseDto;
import com.junwoo.ott.global.customenum.MembershipType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Membership {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long membershipId;
  private MembershipType membershipType;
  private Long price;

  public MemberShipResponseDto toResponseDto() {
    return MemberShipResponseDto.builder()
        .membershipId(membershipId)
        .membershipType(membershipType)
        .price(price)
        .build();
  }

}
