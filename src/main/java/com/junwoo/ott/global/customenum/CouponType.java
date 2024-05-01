package com.junwoo.ott.global.customenum;

import lombok.Getter;

@Getter
public enum CouponType {
  FIX("FIX"),
  RATIO("RATIO");

  private final String message;

  CouponType(final String message) {
    this.message = message;
  }

}
