package com.junwoo.ott.domain.auth.service;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;

import com.junwoo.ott.domain.admin.AdminTestValues;
import com.junwoo.ott.domain.admin.service.AdminService;
import com.junwoo.ott.domain.auth.dto.request.AuthAdminSignupRequestDto;
import com.junwoo.ott.domain.auth.dto.request.AuthSignupRequestDto;
import com.junwoo.ott.domain.user.UserTestValues;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.exception.custom.AdminKeyNotCorrectException;
import com.junwoo.ott.global.exception.custom.UsernameAlreadyExistException;
import com.junwoo.ott.global.exception.custom.UsernameHasAdminPreFixException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest implements UserTestValues, AdminTestValues {

  @InjectMocks
  private AuthService authService;
  @Mock
  private UserService userService;
  @Mock
  private AdminService adminService;


  @BeforeEach
  void setUp() {
    ReflectionTestUtils.setField(authService, "adminKey", TEST_ADMIN_KEY);
    ReflectionTestUtils.setField(authService, "adminPrefix", TEST_ADMIN_PREFIX);
  }

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
    void 어스_회원가입_실패_테스트_중복이름_가입시도() {

      // given
      doThrow(UsernameAlreadyExistException.class)
          .when(userService).validateUserNotExist(anyString());

      // when, then
      Assertions.assertThrows(UsernameAlreadyExistException.class,
          () -> authService.signup(TEST_AUTH_SIGNUP_REQUEST_DTO));
    }

    @Test
    void 어스_회원가입_실패_테스트_이름_관리자_접두사포함() {

      // given
      AuthSignupRequestDto authSignupRequestDto = TEST_AUTH_SIGNUP_REQUEST_DTO_STARTWITHS_ADMIN_PREFIX;

      // when, then
      Assertions.assertThrows(UsernameHasAdminPreFixException.class,
          () -> authService.signup(authSignupRequestDto));
    }


    @Test
    void 어스_관리자_회원가입_성공_테스트() {

      // given
      AuthAdminSignupRequestDto authAdminSignupRequestDto = TEST_AUTH_ADMIN_SIGNUP_REQUEST_DTO;

      // when
      authService.adminSignup(authAdminSignupRequestDto);

      // then
      then(adminService).should(times(1))
          .validateAdminNotExist(authAdminSignupRequestDto.getUsername());
      then(adminService).should(times(1))
          .createAdmin(authAdminSignupRequestDto);
    }

    @Test
    void 어스_관리자_회원가입_실패_테스트_중복이름_가입시도() {

      // given
      AuthAdminSignupRequestDto authAdminSignupRequestDto =
          TEST_AUTH_ADMIN_SIGNUP_REQUEST_DTO;
      doThrow(UsernameAlreadyExistException.class)
          .when(adminService).validateAdminNotExist(anyString());
      // when, then
      Assertions.assertThrows(UsernameAlreadyExistException.class,
          () -> authService.adminSignup(authAdminSignupRequestDto));
    }

    @Test
    void 어스_관리자_회원가입_실패_테스트_관리자키_불일치() {

      // given
      AuthAdminSignupRequestDto authAdminSignupRequestDto =
          TEST_AUTH_ADMIN_SIGNUP_REQUEST_DTO_ADMIN_KEY_INVALID;

      // when, then
      Assertions.assertThrows(AdminKeyNotCorrectException.class,
          () -> authService.adminSignup(authAdminSignupRequestDto));
    }
  }

}
