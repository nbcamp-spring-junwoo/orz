package com.junwoo.ott.global.customenum.payment.cache;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TransactionType {

  CONFIRM("발급"),
  CANCEL("취소"),
  ;

  private final String description;

}
