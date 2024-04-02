package com.junwoo.ott.global.customenum.payment.card;

import static com.fasterxml.jackson.annotation.JsonCreator.Mode;

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

  @JsonCreator(mode = Mode.DELEGATING)
  public static CardType of(final String description) {
    for (CardType cardType : CardType.values()) {
      if (cardType.getDescription().equals(description)) {
        return cardType;
      }
    }

    throw new IllegalArgumentException("No matching constant for [" + description + "]");
  }

}
