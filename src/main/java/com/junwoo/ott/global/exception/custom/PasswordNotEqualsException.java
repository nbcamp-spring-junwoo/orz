package com.junwoo.ott.global.exception.custom;

public class PasswordNotEqualsException extends RuntimeException {

  public PasswordNotEqualsException(final String message) {
    super(message);
  }

}
