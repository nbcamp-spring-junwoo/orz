package com.junwoo.ott.global.exception;

import com.junwoo.ott.global.common.dto.ExceptionDto;
import com.junwoo.ott.global.exception.custom.AdminKeyNotCorrectException;
import com.junwoo.ott.global.exception.custom.CustomAnnouncementException;
import com.junwoo.ott.global.exception.custom.CustomCardException;
import com.junwoo.ott.global.exception.custom.CustomCouponException;
import com.junwoo.ott.global.exception.custom.CustomInvalidDeadLineException;
import com.junwoo.ott.global.exception.custom.CustomLockException;
import com.junwoo.ott.global.exception.custom.CustomPaymentException;
import com.junwoo.ott.global.exception.custom.DuplicatedEmailException;
import com.junwoo.ott.global.exception.custom.ElasticException;
import com.junwoo.ott.global.exception.custom.NotExistMembershipTypeException;
import com.junwoo.ott.global.exception.custom.PasswordNotEqualsException;
import com.junwoo.ott.global.exception.custom.SubscriptionException;
import com.junwoo.ott.global.exception.custom.TokenNotValidException;
import com.junwoo.ott.global.exception.custom.UserNotFoundException;
import com.junwoo.ott.global.exception.custom.UserNotSameException;
import com.junwoo.ott.global.exception.custom.UsernameAlreadyExistException;
import com.junwoo.ott.global.exception.custom.UsernameHasAdminPreFixException;
import com.junwoo.ott.global.exception.custom.VideoNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import java.util.Objects;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
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
    return createResponse(HttpStatus.REQUEST_TIMEOUT, e.getMessage());
  }

  @ExceptionHandler(EntityNotFoundException.class)
  public ResponseEntity<ExceptionDto> entityNotFoundException(final EntityNotFoundException e) {
    log.error("EntityNotFoundException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(CustomInvalidDeadLineException.class)
  public ResponseEntity<ExceptionDto> customInvalidDeadLineException(
      final CustomInvalidDeadLineException e) {
    log.error("CustomInvalidDeadLineException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(CustomCouponException.class)
  public ResponseEntity<ExceptionDto> customCouponException(final CustomCouponException e) {
    log.error("CustomCouponException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(CustomCardException.class)
  public ResponseEntity<ExceptionDto> customCardException(final CustomCardException e) {
    log.error("CustomCardException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(SubscriptionException.class)
  public ResponseEntity<ExceptionDto> subscriptionException(final SubscriptionException e) {
    log.error("SubscriptionException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(CustomPaymentException.class)
  public ResponseEntity<ExceptionDto> customPaymentException(final CustomPaymentException e) {
    log.error("CustomPaymentException: ", e);
    return createResponse(HttpStatus.BAD_GATEWAY, "결제사와 통신하는 도중 오류가 발생했습니다.");
  }

  @ExceptionHandler(ElasticException.class)
  public ResponseEntity<ExceptionDto> elasticsearchException(final ElasticException e) {
    log.error("ElasticsearchException: ", e);
    return createResponse(HttpStatus.BAD_GATEWAY, e.getMessage());
  }

  @ExceptionHandler(CustomAnnouncementException.class)
  public ResponseEntity<ExceptionDto> customAnnouncementException(
      final CustomAnnouncementException e) {
    log.error("CustomAnnouncementException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(UsernameAlreadyExistException.class)
  ResponseEntity<ExceptionDto> usernameAlreadyExistException(final UsernameAlreadyExistException e) {
    log.error("UsernameAlreadyExistException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(AdminKeyNotCorrectException.class)
  ResponseEntity<ExceptionDto> adminKeyNotCorrectException(final AdminKeyNotCorrectException e) {
    log.error("AdminKeyNotCorrectException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(UsernameHasAdminPreFixException.class)
  ResponseEntity<ExceptionDto> usernameHasAdminPreFixException(final UsernameHasAdminPreFixException e) {
    log.error("UsernameHasAdminPreFixException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(VideoNotFoundException.class)
  ResponseEntity<ExceptionDto> videoNotFoundException(final VideoNotFoundException e) {
    log.error("VideoNotFoundException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(UserNotFoundException.class)
  ResponseEntity<ExceptionDto> userNotFoundException(final UserNotFoundException e) {
    log.error("UserNotFoundException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(UserNotSameException.class)
  ResponseEntity<ExceptionDto> userNotSameException(final UserNotSameException e) {
    log.error("UserNotSameException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(PasswordNotEqualsException.class)
  ResponseEntity<ExceptionDto> passwordNotEqualsException(final PasswordNotEqualsException e) {
    log.error("PasswordNotEqualsException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(DuplicatedEmailException.class)
  ResponseEntity<ExceptionDto> duplicatedEmailException(final DuplicatedEmailException e) {
    log.error("DuplicatedEmailException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(NotExistMembershipTypeException.class)
  ResponseEntity<ExceptionDto> notExistMembershipTypeException(final NotExistMembershipTypeException e) {
    log.error("NotExistMembershipTypeException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
  }

  @ExceptionHandler(BadCredentialsException.class)
  ResponseEntity<ExceptionDto> badCredentialsException(final BadCredentialsException e) {
    log.error("BadCredentialsException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, "로그인 정보가 시스템에 있는 정보와 다릅니다");
  }

  @ExceptionHandler(TokenNotValidException.class)
  ResponseEntity<ExceptionDto> tokenNotValidException(final TokenNotValidException e) {
    log.error("TokenNotValidException: ", e);
    return createResponse(HttpStatus.BAD_REQUEST, e.getMessage());
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
