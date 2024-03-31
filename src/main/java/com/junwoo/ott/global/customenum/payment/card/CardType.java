package com.junwoo.ott.global.customenum.payment.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardType {

  CREDIT("신용"),
  CHECK("체크"),
  GIFT("기프트"),
  UNKNOWN("미확인"),
  ;
  private final String description;

  @JsonCreator
  public static CardType of(final String description) {
    for (CardType cardType : CardType.values()) {
      if (cardType.getDescription().equals(description)) {
        return cardType;
      }
    }

    throw new IllegalArgumentException("No matching constant for [" + description + "]");
  }

}
