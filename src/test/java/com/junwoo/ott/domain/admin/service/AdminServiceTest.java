package com.junwoo.ott.domain.admin.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.junwoo.ott.domain.admin.AdminTestValues;
import com.junwoo.ott.domain.admin.repository.AdminRepository;
import com.junwoo.ott.domain.auth.dto.request.AuthAdminSignupRequestDto;
import com.junwoo.ott.global.exception.custom.UsernameAlreadyExistException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AdminServiceTest implements AdminTestValues {

  @InjectMocks
  private AdminService adminService;

  @Mock
  private AdminRepository adminRepository;


  @Nested
  @DisplayName("관리자 회원가입 테스트")
  public class AdminSignupTest {

    @Test
    void 관리자_회원가입_성공_테스트() {

      // given
      AuthAdminSignupRequestDto authAdminSignupRequestDto = TEST_AUTH_ADMIN_SIGNUP_REQUEST_DTO;
      given(adminRepository.existsByUsername(anyString())).willReturn(false);

      // when
      adminService.createAdmin(authAdminSignupRequestDto);

      // then
      then(adminRepository).should(times(1)).existsByUsername(anyString());
    }

    @Test
    void 관리자_회원가입_실패_테스트_중복이름_가입요청() {

      // given
      AuthAdminSignupRequestDto authAdminSignupRequestDto = TEST_AUTH_ADMIN_SIGNUP_REQUEST_DTO;
      given(adminRepository.existsByUsername(anyString())).willReturn(true);

      // when, then
      assertThrows(UsernameAlreadyExistException.class,
          () -> adminService.createAdmin(authAdminSignupRequestDto));
    }

  }

}