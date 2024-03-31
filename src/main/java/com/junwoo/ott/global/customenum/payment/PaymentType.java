package com.junwoo.ott.global.customenum.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentType {

  NORMAL("일반결제"),
  BILLING("자동결제"),
  BRANDPAY("브랜드페이");

  private final String type;

}
