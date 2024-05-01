package com.junwoo.ott.global.customenum.payment.card;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CardOwnerType {

  PERSONAL("개인"),
  COMPANY("법인"),
  UNKNOWN("미확인"),
  ;

  private final String description;

  @JsonCreator(mode = Mode.DELEGATING)
  public static CardOwnerType of(final String description) {
    for (CardOwnerType cardOwnerType : CardOwnerType.values()) {
      if (cardOwnerType.getDescription().equals(description)) {
        return cardOwnerType;
      }
    }

    throw new IllegalArgumentException("No matching constant for [" + description + "]");
  }

}
