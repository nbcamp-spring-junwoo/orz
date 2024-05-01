package com.junwoo.ott.global.exception.custom;

public class UsernameAlreadyExistException extends RuntimeException {

  public UsernameAlreadyExistException(final String message) {
    super(message);
  }

}
