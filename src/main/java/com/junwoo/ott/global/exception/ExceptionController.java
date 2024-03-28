package com.junwoo.ott.global.exception;

import com.junwoo.ott.global.common.dto.ExceptionDto;
import com.junwoo.ott.global.exception.custom.CustomLockException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j(topic = "ExceptionController")
@ControllerAdvice
public class ExceptionController {

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ExceptionDto> handleMethodArgumentNotValidException(
      MethodArgumentNotValidException e
  ) {
    log.error("MethodArgumentNotValidException: ", e);
    return createResponse(
        HttpStatus.BAD_REQUEST,
        Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()
    );
  }

  @ExceptionHandler(CustomLockException.class)
  public ResponseEntity<ExceptionDto> customLockException(CustomLockException e) {
    log.error("CustomLockException: ", e);
    return createResponse(HttpStatus.REQUEST_TIMEOUT, "Lock Exception: " + e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<ExceptionDto> exception(Exception e) {
    log.error("Exception: ", e);
    return createResponse(HttpStatus.I_AM_A_TEAPOT, "Unknown Error: " + e.getMessage());
  }

  private ResponseEntity<ExceptionDto> createResponse(HttpStatus status, String message) {
    return ResponseEntity.status(status.value())
        .body(new ExceptionDto(message));
  }

}
