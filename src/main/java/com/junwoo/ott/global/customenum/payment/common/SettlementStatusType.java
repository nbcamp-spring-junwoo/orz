package com.junwoo.ott.global.customenum.payment.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SettlementStatusType {

  INCOMPLETED("정산 미완료"),
  COMPLETED("정산 완료");

  private final String description;

}
