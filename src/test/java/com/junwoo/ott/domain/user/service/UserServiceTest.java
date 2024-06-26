package com.junwoo.ott.domain.user.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.junwoo.ott.domain.auth.dto.request.AuthLoginRequestDto;
import com.junwoo.ott.domain.user.UserTestValues;
import com.junwoo.ott.domain.user.dto.response.UserGetKeyResponseDto;
import com.junwoo.ott.domain.user.dto.response.UserReadResponseDto;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.repository.UserRepository;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.exception.custom.PasswordNotEqualsException;
import com.junwoo.ott.global.exception.custom.UserNotFoundException;
import com.junwoo.ott.global.exception.custom.UserNotSameException;
import com.junwoo.ott.global.exception.custom.UsernameAlreadyExistException;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceTest implements UserTestValues {

  @InjectMocks
  private UserService userService;
  @Mock
  private UserRepository userRepository;
  @Mock
  private PasswordEncoder passwordEncoder;
  @Mock
  private AuthenticationManager authenticationManager;

  @Nested
  @DisplayName("회원가입 테스트")
  class UserSignupTest {

    @Test
    void 회원가입_성공_테스트() {

      // given
      given(passwordEncoder.encode(anyString())).willReturn(TEST_ENCRYPT_PASSWORD);

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
      assertThrows(UsernameAlreadyExistException.class,
          () -> userService.validateUserNotExist(anyString()));
    }

  }

  @Nested
  @DisplayName("회원 로그인 테스트")
  class UserLoginTest {

    @Test
    void 회원_로그인_성공_테스트() {

      // given
      AuthLoginRequestDto authLoginRequestDto = TEST_AUTH_LOGIN_REQUEST_DTO;

      // when
      userService.login(authLoginRequestDto);

      // then
      then(authenticationManager).should(times(1)).authenticate(any());
    }

  }

  @Nested
  @DisplayName("회원 단건 조회 테스트")
  class UserGetTest {

    @Test
    void 회원_단건_조회_성공_테스트() {

      // given
      given(userRepository.findById(anyLong())).willReturn(Optional.of(TEST_USER));

      // when
      UserReadResponseDto userReadResponseDto = userService.getUser(anyLong());

      // then
      assertEquals(TEST_USER_ID, userReadResponseDto.getUserId());
      assertEquals(TEST_USERNAME, userReadResponseDto.getUsername());
    }

    @Test
    void 회원_단건_조회_실패_테스트() {

      // given
      given(userRepository.findById(anyLong())).willReturn(Optional.empty());

      // when, then
      assertThrows(UserNotFoundException.class,
          () -> userService.getUser(anyLong()));
    }
  }

  @Nested
  @DisplayName("회원정보 수정 테스트")
  class UserPutTest {

    @Test
    void 회원정보_수정_성공_테스트() {

      // given
      given(userRepository.findById(anyLong())).willReturn(Optional.of(TEST_USER));
      given(passwordEncoder.matches(anyString(), anyString())).willReturn(true);

      // when
      userService.putUser(TEST_USER_PUT_REQUEST_DTO);

      // then

    }

    @Test
    void 회원정보_수정_실패_테스트_회원아이디_불일치() {

      // given

      // when, then
      assertThrows(UserNotSameException.class,
          () -> userService.putUser(TEST_USER_PUT_REQUEST_DTO_MISMATCH));

    }


    @Test
    void 회원정보_수정_실패_테스트_회원없음() {

      // given
      given(userRepository.findById(anyLong())).willReturn(Optional.empty());

      // when, then
      assertThrows(UserNotFoundException.class,
          () -> userService.putUser(TEST_USER_PUT_REQUEST_DTO));
    }

    @Test
    void 회원정보_수정_실패_테스트_비밀번호_불일치() {

      // given
      given(userRepository.findById(anyLong())).willReturn(Optional.of(TEST_USER));
      given(passwordEncoder.matches(any(), any())).willReturn(false);

      // when, then
      assertThrows(PasswordNotEqualsException.class,
          () -> userService.putUser(TEST_USER_PUT_REQUEST_DTO));
    }
  }


  @Nested
  @DisplayName("회원 맴버십 등급 변경 테스트")
  class UserMembershipUpdateTest {

    @Test
    void 회원_맴버십_변경_성공_테스트() {

      // given
      given(userRepository.findById(anyLong())).willReturn(Optional.of(TEST_USER));

      // when
      userService.updateUserMembership(TEST_USER_ID, MembershipType.ROLE_NORMAL);
    }

    @Test
    void 회원_맴버십_변경_실패_테스트() {

      // given
      given(userRepository.findById(anyLong())).willReturn(Optional.empty());

      // when, then
      assertThrows(UserNotFoundException.class,
          () -> userService.getKey(TEST_USER_GET_KEY_REQUEST_DTO));
    }

  }

  @Nested
  @DisplayName("회원 커스텀키 반환 테스트")
  class UserGetKeyTest {

    @Test
    void 회원_커스텀키_반환_성공_테스트() {

      // given
      given(userRepository.findById(anyLong())).willReturn(Optional.of(TEST_USER));

      // when
      UserGetKeyResponseDto userGetKeyResponseDto = userService.getKey(
          TEST_USER_GET_KEY_REQUEST_DTO);

      // then
      assertEquals(TEST_USER_CUSTOMER_KEY, userGetKeyResponseDto.getCustomerKey());
    }

    @Test
    void 회원_커스텀키_반환_실패_테스트() {

      // given
      given(userRepository.findById(anyLong())).willReturn(Optional.empty());

      // when, then
      assertThrows(UserNotFoundException.class,
          () -> userService.getKey(TEST_USER_GET_KEY_REQUEST_DTO));
    }

  }

}
