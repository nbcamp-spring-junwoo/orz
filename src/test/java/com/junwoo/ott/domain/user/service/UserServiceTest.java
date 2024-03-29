package com.junwoo.ott.domain.user.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.junwoo.ott.domain.user.UserTestValues;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.repository.UserRepository;
import com.junwoo.ott.global.exception.custom.UsernameAlreadyExistException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest implements UserTestValues {

  @InjectMocks
  private UserService userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;

  @Nested
  @DisplayName("회원가입 테스트")
  class UserSignupTest {

    @Test
    void 회원가입_성공_테스트() {

      // given
      given(passwordEncoder.encode(anyString())).willReturn(ENCRYPT_PASSWORD);

      // when
      userService.createUser(TEST_AUTH_SIGNUP_REQUEST_DTO);

      // then
      then(userRepository).should(times(1)).save(any(User.class));
    }

    @Test
    void 중복유저네임_검증_테스트() {
      // given
      given(userRepository.existsByUsername(anyString())).willReturn(true);

      // when, then
      Assertions.assertThrows(UsernameAlreadyExistException.class,
          () -> userService.validateUserNotExist(anyString()));
    }

  }

}