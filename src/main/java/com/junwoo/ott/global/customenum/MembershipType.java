package com.junwoo.ott.global.customenum;

import lombok.Getter;

@Getter
public enum MembershipType {
  NORMAL("NORMAL"),
  BRONZE("BRONZE"),
  SILVER("SILVER"),
  GOLD("GOLD"),
  ;

  private final String grade;

  MembershipType(String grade) {
    this.grade = grade;
  }

}
