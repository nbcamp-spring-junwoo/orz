package com.junwoo.ott.global.exception.custom;

public class LoginFailException extends RuntimeException {

  public LoginFailException(final String message) {
    super(message);
  }

}
