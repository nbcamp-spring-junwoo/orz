package com.junwoo.ott.global.customenum;

import lombok.Getter;

@Getter
public enum OauthType {
  KAKAO("KAKAO"),
  ;

  private final String oauthType;

  OauthType(String oauthType) {
    this.oauthType = oauthType;
  }
}
