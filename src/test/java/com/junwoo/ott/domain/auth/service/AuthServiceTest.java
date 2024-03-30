package com.junwoo.ott.domain.auth.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

import com.junwoo.ott.domain.auth.dto.request.AuthSignupRequestDto;
import com.junwoo.ott.domain.user.UserTestValues;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.exception.custom.UsernameAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest implements UserTestValues {

  @InjectMocks
  private AuthService authService;
  @Mock
  private UserService userService;

  @Nested
  @DisplayName("AuthService 회원가입 테스트")
  class AuthSignupTest {

    @Test
    void 어스_회원가입_성공_테스트() {

      // given
      AuthSignupRequestDto authSignupRequestDto = TEST_AUTH_SIGNUP_REQUEST_DTO;
      // when
      authService.signup(authSignupRequestDto);
      // then
      then(userService).should(times(1))
          .validateUserNotExist(authSignupRequestDto.getUsername());
      then(userService).should(times(1))
          .createUser(authSignupRequestDto);
    }

    @Test
    void 어스_회원가입_실패_테스트() {
      // given
      doThrow(UsernameAlreadyExistException.class)
          .when(userService).validateUserNotExist(anyString());

      // when, then
      Assertions.assertThrows(UsernameAlreadyExistException.class,
          () -> authService.signup(TEST_AUTH_SIGNUP_REQUEST_DTO));
    }

  }

}
