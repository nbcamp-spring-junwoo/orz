package com.junwoo.ott.global.exception;

import com.junwoo.ott.global.common.dto.ExceptionDto;
import com.junwoo.ott.global.exception.custom.CustomAnnouncementException;
import com.junwoo.ott.global.exception.custom.CustomCardException;
import com.junwoo.ott.global.exception.custom.CustomCouponException;
import com.junwoo.ott.global.exception.custom.CustomInvalidDeadLineException;
import com.junwoo.ott.global.exception.custom.CustomLockException;
import com.junwoo.ott.global.exception.custom.CustomPaymentException;
import com.junwoo.ott.global.exception.custom.ElasticException;
import com.junwoo.ott.global.exception.custom.SubscriptionException;
import jakarta.persistence.EntityNotFoundException;
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
      final MethodArgumentNotValidException e
  ) {
    log.error("MethodArgumentNotValidException: ", e);
    return createResponse(
        HttpStatus.BAD_REQUEST,
        Objects.requireNonNull(e.getBindingResult().getFieldError()).getDefaultMessage()
    );
  }

  @ExceptionHandler(CustomLockException.class)
  public ResponseEntity<ExceptionDto> customLockException(final CustomLockException e) {
    log.error("CustomLockException: ", e);
    return createResponse(HttpStatus.REQUEST_TIMEOUT, "Lock Exception: " + e.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ExceptionDto> entityNotFoundException(final EntityNotFoundException e) {
    log.error("EntityNotFoundException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, "EntityNotFoundException: " + e.getMessage());
  }

  @ExceptionHandler(CustomInvalidDeadLineException.class)
  public ResponseEntity<ExceptionDto> customInvalidDeadLineException(
      final CustomInvalidDeadLineException e) {
    log.error("CustomInvalidDeadLineException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST,
        "CustomInvalidDeadLineException: " + e.getMessage());
  }

  @ExceptionHandler(CustomCouponException.class)
  public ResponseEntity<ExceptionDto> customCouponException(final CustomCouponException e) {
    log.error("CustomCouponException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST,
        "CustomCouponException: " + e.getMessage());
  }

  @ExceptionHandler(CustomCardException.class)
  public ResponseEntity<ExceptionDto> customCardException(final CustomCardException e) {
    log.error("CustomCardException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, "CustomCardException: " + e.getMessage());
  }

  @ExceptionHandler(SubscriptionException.class)
  public ResponseEntity<ExceptionDto> subscriptionException(final SubscriptionException e) {
    log.error("SubscriptionException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, "SubscriptionException: " + e.getMessage());
  }

  @ExceptionHandler(CustomPaymentException.class)
  public ResponseEntity<ExceptionDto> customPaymentException(final CustomPaymentException e) {
    log.error("CustomPaymentException: ", e);
    return createResponse(e.getHttpStatus(), "CustomPaymentException: " + e.getMessage());
  }

  @ExceptionHandler(ElasticException.class)
  public ResponseEntity<ExceptionDto> elasticsearchException(final ElasticException e) {
    log.error("ElasticsearchException: ", e);
    return createResponse(HttpStatus.BAD_GATEWAY, "ElasticsearchException:" + e.getMessage());
  }

  @ExceptionHandler(CustomAnnouncementException.class)
  public ResponseEntity<ExceptionDto> customAnnouncementException(
      final CustomAnnouncementException e) {
    log.error("CustomAnnouncementException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, "CustomAnnouncementException: " + e.getMessage());
  }

  @ExceptionHandler(Exception.class)
  ResponseEntity<ExceptionDto> exception(final Exception e) {
    log.error("Exception: ", e);
    return createResponse(HttpStatus.I_AM_A_TEAPOT, "Unknown Error: " + e.getMessage());
  }

  private ResponseEntity<ExceptionDto> createResponse(
      final HttpStatus status,
      final String message
  ) {
    return ResponseEntity.status(status.value())
        .body(new ExceptionDto(message));
  }

}
