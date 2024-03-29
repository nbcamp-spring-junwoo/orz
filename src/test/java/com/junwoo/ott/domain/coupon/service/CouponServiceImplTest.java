package com.junwoo.ott.domain.coupon.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponCreateResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponReadResponseDto;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.domain.coupon.repository.CouponIssuanceRepository;
import com.junwoo.ott.domain.coupon.repository.CouponRepository;
import com.junwoo.ott.domain.coupon.test.CouponTestValues;
import com.junwoo.ott.domain.coupon.test.CouponUserTestValues;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
class CouponServiceImplTest implements CouponTestValues, CouponUserTestValues {

  @Mock
  private CouponRepository couponRepository;

  @Mock
  private CouponIssuanceRepository couponIssuanceRepository;

  @InjectMocks
  private CouponService couponService;

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

      Assertions.assertEquals(result.getCouponType(), createRequestDto.getType());
      Assertions.assertEquals(result.getStartAt(), createRequestDto.getStartAt());
      Assertions.assertEquals(result.getEndAt(), createRequestDto.getEndAt());
    }

  }

  @Nested
  @DisplayName("쿠폰 테이블 조회")
  class ReadCoupon {

    @Test
    @DisplayName("쿠폰 테이블 조회 성공")
    void 쿠폰_테이블_조회_성공() {
      // given
      given(couponRepository.getCoupons(any())).willReturn(TEST_COUPONS);

      // when
      Page<CouponReadResponseDto> result = couponService.getCoupon(TEST_COUPON_READ_REQUEST_DTO);

      // then
      Assertions.assertEquals(result.getContent().size(), TEST_COUPON_SIZE);
      Assertions.assertEquals(result.getContent().get(0).getCount(),
          TEST_COUPON_CREATE_REQUEST_DTO.getCount());
      Assertions.assertEquals(result.getContent().get(0).getCouponType(),
          TEST_COUPON_CREATE_REQUEST_DTO.getType());
      Assertions.assertEquals(result.getContent().get(0).getDiscount(),
          TEST_COUPON_CREATE_REQUEST_DTO.getDiscount());
    }

  }

  @Nested
  @DisplayName("쿠폰 발급 테이블 조회")
  class ReadCouponIssuance {

    @Test
    @DisplayName("쿠폰 발급 테이블 조회 성공")
    void 쿠폰_발급테이블_조회_성공() {
      // given
      Page<CouponIssuance> couponIssuance = new PageImpl<>(
          List.of(CouponIssuance.of(TEST_COUPON_V2, TEST_USER)));
      given(couponIssuanceRepository.getCouponIssuance(any(), any())).willReturn(couponIssuance);

      // when
      Page<CouponIssuanceReadResponseDto> result = couponService.getCoupons(
          TEST_COUPON_READ_REQUEST_DTO);

      // then
      Assertions.assertEquals(result.getContent().get(0).getCouponId(),
          TEST_COUPON_V2.getCouponId());
      Assertions.assertEquals(result.getContent().get(0).getCouponType(),
          TEST_COUPON_V1.getCouponType());
      Assertions.assertEquals(result.getContent().get(0).getMembershipType(),
          TEST_COUPON_V2.getMembershipType());
    }

  }

}
