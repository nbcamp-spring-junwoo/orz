package com.junwoo.ott.global.customenum.payment.cache;

import com.fasterxml.jackson.annotation.JsonCreator;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheReceiptType {

  DEDUCTION("소득공제"),
  CONSUMPTION("지출증빙"),
  ;

  private final String description;

  @JsonCreator
  public static CacheReceiptType of(final String description) {
    for (CacheReceiptType cacheReceiptType : CacheReceiptType.values()) {
      if (cacheReceiptType.getDescription().equals(description)) {
        return cacheReceiptType;
      }
    }

    throw new IllegalArgumentException("No matching constant for [" + description + "]");
  }

}
