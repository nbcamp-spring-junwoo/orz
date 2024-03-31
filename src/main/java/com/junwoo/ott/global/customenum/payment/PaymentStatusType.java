package com.junwoo.ott.global.customenum.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatusType {

  READY("결제 준비중"),
  IN_PROGRESS("결제 진행중"),
  WAITING_FOR_DEPOSIT("결제 대기중"),
  DONE("결제 완료"),
  CANCELED("결제 취소"),
  PARTIAL_CANCELED("부분 취소"),
  ABORTED("결제 승인 실패"),
  EXPIRED("결제 만료"),
  ;

  private final String description;

}
