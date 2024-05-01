package com.junwoo.ott.global.customenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SubscriptionStatusType {

  ACTIVE("활성화"),
  EXPIRED("만료"),
  CANCELED("취소");

  private final String description;

}
