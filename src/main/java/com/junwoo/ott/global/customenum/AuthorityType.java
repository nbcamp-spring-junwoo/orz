package com.junwoo.ott.global.customenum;

import lombok.Getter;

@Getter
public enum AuthorityType {

  USER("USER"),
  ADMIN("ADMIN"),
  ;

  private final String authority;

  AuthorityType(String authority) {
    this.authority = authority;
  }

  @Override
  public String toString() {
    return authority;
  }
}
