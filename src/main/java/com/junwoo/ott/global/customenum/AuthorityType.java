package com.junwoo.ott.global.customenum;

import com.junwoo.ott.global.exception.custom.NotExistAuthorityTypeException;
import lombok.Getter;

@Getter
public enum AuthorityType {

  ROLE_USER("ROLE_USER"),
  ROLE_ADMIN("ROLE_ADMIN"),
  ;

  private final String authority;

  AuthorityType(final String authority) {
    this.authority = authority;
  }

  @Override
  public String toString() {
    return authority;
  }

  public static AuthorityType fromString(final String authority) {
    for (AuthorityType type : AuthorityType.values()) {
      if (type.authority.equals(authority)) {
        return type;
      }
    }
    throw new NotExistAuthorityTypeException("존재하지 않는 권한입니다.");
  }

}
