package com.junwoo.ott.global.exception.custom;

public class UsernameAlreadyExistException extends RuntimeException {

  public UsernameAlreadyExistException(String message) {
    super(message);
  }

}
