package com.junwoo.ott.global.customenum;

import lombok.Getter;

@Getter
public enum StateType {

  ACTIVATION("ACTIVATION"),
  INACTIVATION("INACTIVATION"),
  SECESSION("SECESSION"),
  ;

  private final String state;

  StateType(final String state) {
    this.state = state;
  }

}
