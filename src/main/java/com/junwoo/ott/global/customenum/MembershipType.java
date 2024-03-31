package com.junwoo.ott.global.customenum;

import com.junwoo.ott.global.exception.custom.NotExistMembershipTypeException;
import lombok.Getter;

@Getter
public enum MembershipType {
  ROLE_NORMAL("ROLE_NORMAL"),
  ROLE_BRONZE("ROLE_BRONZE"),
  ROLE_SILVER("ROLE_SILVER"),
  ROLE_GOLD("ROLE_GOLD"),
  ;

  private final String membership;

  MembershipType(String membership) {
    this.membership = membership;
  }

  @Override
  public String toString() {
    return membership;
  }

  public static MembershipType fromString(String membershipType) {
    for (MembershipType type : MembershipType.values()) {
      if (type.membership.equals(membershipType)) {
        return type;
      }
    }
    throw new NotExistMembershipTypeException("존재하지 않는 맴버십등급 입니다.");
  }
}
