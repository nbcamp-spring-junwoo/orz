package com.junwoo.ott.global.customenum;

import lombok.Getter;

@Getter
public enum MembershipType {
  NORMAL("ROLE_NORMAL"),
  BRONZE("ROLE_BRONZE"),
  SILVER("ROLE_SILVER"),
  GOLD("ROLE_GOLD"),
  ;

  private final String membership;

  MembershipType(String membership) {
    this.membership = membership;
  }

  @Override
  public String toString() {
    return membership;
  }
}
