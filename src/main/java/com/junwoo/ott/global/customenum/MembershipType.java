package com.junwoo.ott.global.customenum;

import lombok.Getter;

@Getter
public enum MembershipType {
  NORMAL("NORMAL"),
  BRONZE("BRONZE"),
  SILVER("SILVER"),
  GOLD("GOLD"),
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
