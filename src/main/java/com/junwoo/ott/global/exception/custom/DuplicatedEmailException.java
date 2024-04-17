package com.junwoo.ott.global.exception.custom;

public class DuplicatedEmailException extends RuntimeException {

  public DuplicatedEmailException(final String message) {
    super(message);
  }

}
