package com.junwoo.ott.global.customenum.payment.virtualaccount;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RefundStatusType {

  NONE("환불 요청 없음"),
  PENDING(" 환불 처리중"),
  FAILED("환불 실패"),
  PARTIAL_FAILED("부분 환불 실패"),
  COMPLETED("환불 완료"),
  ;

  private final String description;

}
