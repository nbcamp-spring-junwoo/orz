package com.junwoo.ott.global.exception.custom;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class CustomPaymentException extends RuntimeException {

  protected final HttpStatus httpStatus;

  public CustomPaymentException(final String message, final HttpStatus httpStatus) {
    super(message);
    this.httpStatus = httpStatus;
  }

}
