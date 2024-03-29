package com.junwoo.ott.domain.coupon.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponCreateResponseDto;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.domain.coupon.repository.CouponRepository;
import com.junwoo.ott.domain.coupon.test.CouponTest;
import com.junwoo.ott.global.exception.custom.CustomTypeException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CouponServiceImplTest implements CouponTest {

  @Mock
  private CouponRepository couponRepository;

  @InjectMocks
  private CouponServiceImpl couponService;

  @Nested
  @DisplayName("쿠폰 생성에 대한 테스트")
  class CreateCoupon {

    @Test
    @DisplayName("쿠폰 생성 성공")
    void 쿠폰_생성() {
      // given
      CouponCreateRequestDto createRequestDto = TEST_COUPON_CREATE_REQUEST_DTO;
      Coupon coupon = Coupon.of(createRequestDto);

      given(couponRepository.save(any())).willReturn(coupon);

      CouponCreateResponseDto result = couponService.createCoupon(createRequestDto);

      Assertions.assertEquals(result.getCouponType().getMessage(), createRequestDto.getType());
      Assertions.assertEquals(result.getStartAt(), createRequestDto.getStartAt());
      Assertions.assertEquals(result.getEndAt(), createRequestDto.getEndAt());
    }

    @Test
    @DisplayName("쿠폰 생성 예외발생 - 쿠폰 타입 불일치")
    void 쿠폰_생성_예외발생_쿠폰_타입_불일치() {
      CouponCreateRequestDto dto = new CouponCreateRequestDto(
          TEST_MEMBER_ID,
          TEST_COUPON_COUPON_TYPE_ERROR_DTO
      );

      Assertions.assertThrows(CustomTypeException.class,
          () -> couponService.createCoupon(dto));
    }

    @Test
    @DisplayName("쿠폰 생성 예외발생 - 멤버쉽 타입 불일치")
    void 쿠폰_생성_예외발생_멤버쉽_타입_불일치() {
      CouponCreateRequestDto dto = new CouponCreateRequestDto(
          TEST_MEMBER_ID,
          TEST_MEMBERSHIP_TYPE_ERROR_DTO
      );

      Assertions.assertThrows(CustomTypeException.class,
          () -> couponService.createCoupon(dto));
    }
  }

}
