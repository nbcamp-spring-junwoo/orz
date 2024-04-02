package com.junwoo.ott.global.customenum.payment.virtualaccount;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonCreator.Mode;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum VirtualAccountType {

  COMMON("일반"),
  STATIC("고정"),
  ;

  private final String description;

  @JsonCreator(mode = Mode.DELEGATING)
  public static VirtualAccountType of(final String description) {
    for (VirtualAccountType virtualAccountType : VirtualAccountType.values()) {
      if (virtualAccountType.getDescription().equals(description)) {
        return virtualAccountType;
      }
    }

    throw new IllegalArgumentException("No matching constant for [" + description + "]");
  }

}
