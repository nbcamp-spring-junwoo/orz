package com.junwoo.ott.global.customenum.payment.card;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum InterestPayerType {

  BUYER("구매자"),
  CARD_COMPANY("카드사"),
  MERCHANT("가맹점"),
  ;
  private final String description;

}
