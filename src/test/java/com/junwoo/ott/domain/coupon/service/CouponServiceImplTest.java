package com.junwoo.ott.domain.coupon.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.junwoo.ott.domain.coupon.dto.body.CouponCreateDto;
import com.junwoo.ott.domain.coupon.dto.body.CouponUpdateDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponCreateResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceCreateResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponReadResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponUpdateResponseDto;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.domain.coupon.repository.CouponIssuanceRepository;
import com.junwoo.ott.domain.coupon.repository.CouponRepository;
import com.junwoo.ott.domain.coupon.test.CouponTestValues;
import com.junwoo.ott.domain.coupon.test.CouponUserTestValues;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.exception.custom.CustomInvalidDeadLineException;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
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

  @Mock
  private UserService userService;

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

    @Test
    @DisplayName("쿠폰 생성 실패 마감일이 시작보다 빠름")
    void 쿠폰_생성_실패() {

      Assertions.assertThrows(CustomInvalidDeadLineException.class,
          () -> new CouponCreateDto(
              TEST_DESCRIPTION_V1,
              TEST_COUPON_TYPE_V1,
              TEST_MEMBERSHIP_TYPE_V1,
              TEST_DISCOUNT_V1,
              TEST_COUNT_V1,
              TEST_START_AT_V1,
              TEST_END_ERROR_AT
          ));
    }

  }

  @Nested
  @DisplayName("쿠폰 테이블 조회")
  class ReadCoupon {

    @Test
    @DisplayName("쿠폰 테이블 조회 성공")
    void 쿠폰_테이블_조회_성공() {
      // given
      given(couponRepository.findAllByOrderByStartAtDesc(any())).willReturn(TEST_COUPONS);

      // when
      Page<CouponReadResponseDto> result = couponService.getCoupon(TEST_COUPON_READ_REQUEST_DTO);

      // then
      Assertions.assertEquals(result.getContent().size(), TEST_COUPON_SIZE);
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
          TEST_COUPON_V2.getCouponType());
      Assertions.assertEquals(result.getContent().get(0).getMembershipType(),
          TEST_COUPON_V2.getMembershipType());
    }

  }

  @Nested
  @DisplayName("쿠폰 테이블 삭제")
  class DeleteCoupon {

    @Test
    @DisplayName("쿠폰 테이블 삭제 성공")
    void 쿠폰_테이블_삭제_성공() {
      // given
      given(couponRepository.findById(TEST_COUPON_ID)).willReturn(
          Optional.ofNullable(TEST_COUPON_V2));

      // when
      couponService.deleteCoupon(TEST_COUPON_ID);

      // then
      verify(couponRepository, atLeastOnce()).delete(any());
    }

    @Test
    @DisplayName("쿠폰 테이블 삭제 실패 존재하지 않은 쿠폰")
    void 쿠폰_테이블_삭제_실패_존재하지않은_쿠폰() {
      given(couponRepository.findById(any())).willReturn(Optional.empty());

      Assertions.assertThrows(EntityNotFoundException.class,
          () -> couponService.deleteCoupon(any()));
    }

  }

  @Nested
  @DisplayName("쿠폰 테이블 수정")
  class UpdateCoupon {

    @Test
    @DisplayName("쿠폰 테이블 수정 성공")
    void 쿠폰_테이블_수정_성공() {
      // given
      given(couponRepository.findById(TEST_COUPON_ID)).willReturn(
          Optional.ofNullable(TEST_COUPON_V1));

      // when
      CouponUpdateResponseDto result = couponService.updateCoupon(TEST_COUPON_UPDATE_REQUEST_DTO);

      // then
      Assertions.assertEquals(result.getCoupon().getCouponType(), TEST_COUPON_TYPE_V2);
      Assertions.assertEquals(result.getCoupon().getDescription(), TEST_DESCRIPTION_V2);
      Assertions.assertEquals(result.getCoupon().getCount(), TEST_COUNT_V2);
    }

    @Test
    @DisplayName("쿠폰 테이블 수정 실패 마감이 시작보다 더 빠른 경우")
    void 쿠폰_테이블_수정_실패_날짜_수정_실패() {

      Assertions.assertThrows(CustomInvalidDeadLineException.class,
          () -> new CouponUpdateDto(
              TEST_DESCRIPTION_V1,
              TEST_COUPON_TYPE_V1,
              TEST_MEMBERSHIP_TYPE_V1,
              TEST_DISCOUNT_V1,
              TEST_COUNT_V1,
              TEST_START_AT_V1,
              TEST_END_ERROR_AT
          ));
    }

  }

  @Nested
  @DisplayName("쿠폰 발급 테이블 생성")
  class CreateCouponIssuance {

    @Test
    @DisplayName("쿠폰 발급 테이블 생성 성공")
    void 쿠폰_발급_테이블_생성_성공() {
      // given
      Coupon coupon = TEST_COUPON_V1;
      given(couponRepository.findById(TEST_COUPON_ID)).willReturn(
          Optional.ofNullable(coupon));

      // when
      CouponIssuanceCreateResponseDto result = couponService.createCouponIssuance(
          TEST_COUPON_ISSUANCE_CREATE_REQUEST_DTO);

      // then
      Assertions.assertEquals(result.getCoupon(), TEST_COUPON_ISSUANCE.getCoupon());
      Assertions.assertEquals(result.getUser().getUserId(),
          TEST_COUPON_ISSUANCE.getUser().getUserId());
      Assertions.assertEquals(coupon.getCount(), TEST_COUNT_V1 - 1);
    }

    @Test
    @DisplayName("쿠폰 발급 테이블 생성 실패 없는 쿠폰")
    void 쿠폰_발급_테이블_생성_실패_없는쿠폰() {
      // given
      given(couponRepository.findById(any())).willThrow(EntityNotFoundException.class);

      // when, then
      Assertions.assertThrows(EntityNotFoundException.class,
          () -> couponService.createCouponIssuance(TEST_COUPON_ISSUANCE_CREATE_REQUEST_DTO));
    }

  }

}
