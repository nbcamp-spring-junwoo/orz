package com.junwoo.ott.global.customenum.payment.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AcquireStatusType {

  READY("매입 준비"),
  REQUESTED("매입 요청"),
  COMPLETED("매입 완료"),
  CANCEL_REQUESTED("매입 취소 요청"),
  CANCELED("매입 취소 완료"),
  ;

  private final String description;

}
