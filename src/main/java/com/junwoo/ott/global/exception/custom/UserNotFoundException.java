package com.junwoo.ott.global.exception.custom;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException(final String message) {
    super(message);
  }

}
