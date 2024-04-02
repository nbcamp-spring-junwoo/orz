package com.junwoo.ott.global.customenum.payment.common;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethodType {

  CARD("카드"),
  VIRTUAL_ACCOUNT("가상계좌"),
  EASY_PAY("간편결제"),
  MOBILE("휴대폰"),
  ACCOUNT_TRANSFER("계좌이체"),
  CULTURE_VOUCHER("문화상품권"),
  BOOK_CULTURE_VOUCHER("도서문화상품권"),
  GAME_CULTURE_VOUCHER("게임문화상품권");

  private final String description;

  @JsonCreator(mode = Mode.DELEGATING)
  public static PaymentMethodType of(final String description) {
    for (PaymentMethodType paymentMethodType : PaymentMethodType.values()) {
      if (paymentMethodType.getDescription().equals(description)) {
        return paymentMethodType;
      }
    }

    throw new IllegalArgumentException("No matching constant for [" + description + "]");
  }

}
