package com.junwoo.ott.global.exception.custom;

public class TokenNotValidException extends RuntimeException {

  public TokenNotValidException(final String message) {
    super(message);
  }

}
