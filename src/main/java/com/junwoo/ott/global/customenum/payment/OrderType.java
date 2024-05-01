package com.junwoo.ott.global.customenum.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderType {

  PAYMENT("결제", "0"),
  SUBSCRIPTION("구독", "1"),
  ;

  private final String description;
  private final String code;

}
