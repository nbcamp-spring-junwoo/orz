package com.junwoo.ott.global.customenum;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RatingType {

  NOT_RATED(Integer.MIN_VALUE),
  EVERYONE(0),
  RATE7(7),
  RATE12(12),
  RATE15(15),
  RATE18(18),
  RATE19(19),
  RESTRICTED(Integer.MAX_VALUE),
  ;

  private final Integer rating;

}
