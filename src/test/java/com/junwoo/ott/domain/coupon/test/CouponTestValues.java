package com.junwoo.ott.domain.coupon.test;

import static com.junwoo.ott.domain.coupon.test.CouponUserTestValues.TEST_USER;

import com.junwoo.ott.domain.coupon.dto.body.CouponCreateDto;
import com.junwoo.ott.domain.coupon.dto.body.CouponUpdateDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponIssuanceCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponReadRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponUpdateRequestDto;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.global.customenum.CouponType;
import com.junwoo.ott.global.customenum.MembershipType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface CouponTestValues {

  Long TEST_COUPON_USER_ID = 1L;
  Long TEST_COUPON_ID = 1L;
  Integer TEST_PAGE = 1;
  Integer TEST_SIZE = 3;
  Integer TEST_COUPON_SIZE = 1;
  String TEST_DESCRIPTION_V1 = "테스트 설명";
  String TEST_DESCRIPTION_V2 = "테스트 수정 설명";
  CouponType TEST_COUPON_TYPE_V1 = CouponType.FIX;
  CouponType TEST_COUPON_TYPE_V2 = CouponType.RATIO;
  MembershipType TEST_MEMBERSHIP_TYPE_V1 = MembershipType.SILVER;
  MembershipType TEST_MEMBERSHIP_TYPE_V2 = MembershipType.GOLD;
  Integer TEST_DISCOUNT_V1 = 10;
  Integer TEST_DISCOUNT_V2 = 20;
  Integer TEST_COUNT_V1 = 100;
  Integer TEST_COUNT_V2 = 200;
  String TEST_START_AT_V1 = "2024-04-01";
  String TEST_START_AT_V2 = "2024-05-01";
  String TEST_END_ERROR_AT = "1997-03-15";
  String TEST_END_AT_V1 = "2024-04-30";
  String TEST_END_AT_V2 = "2024-05-30";

  Pageable TEST_PAGEABLE = PageRequest.of(TEST_PAGE, TEST_SIZE);

  CouponCreateDto TEST_COUPON_CREATE_DTO = new CouponCreateDto(
      TEST_DESCRIPTION_V1,
      TEST_COUPON_TYPE_V1,
      TEST_MEMBERSHIP_TYPE_V1,
      TEST_DISCOUNT_V1,
      TEST_COUNT_V1,
      TEST_START_AT_V1,
      TEST_END_AT_V1
  );

  CouponUpdateDto TEST_COUPON_UPDATE_DTO = new CouponUpdateDto(
      TEST_DESCRIPTION_V2,
      TEST_COUPON_TYPE_V2,
      TEST_MEMBERSHIP_TYPE_V2,
      TEST_DISCOUNT_V2,
      TEST_COUNT_V2,
      TEST_START_AT_V2,
      TEST_END_AT_V2
  );

  CouponCreateRequestDto TEST_COUPON_CREATE_REQUEST_DTO = new CouponCreateRequestDto(
      TEST_COUPON_CREATE_DTO
  );

  CouponReadRequestDto TEST_COUPON_READ_REQUEST_DTO = new CouponReadRequestDto(TEST_PAGEABLE);

  CouponUpdateRequestDto TEST_COUPON_UPDATE_REQUEST_DTO = new CouponUpdateRequestDto(
      TEST_COUPON_ID,
      TEST_COUPON_UPDATE_DTO
  );

  Coupon TEST_COUPON_V1 = Coupon.of(TEST_COUPON_CREATE_REQUEST_DTO);

  Coupon TEST_COUPON_V2 = Coupon.builder()
      .couponId(TEST_COUPON_ID)
      .description(TEST_DESCRIPTION_V1)
      .couponType(TEST_COUPON_TYPE_V1)
      .membershipType(TEST_MEMBERSHIP_TYPE_V1)
      .count(TEST_COUNT_V1)
      .discount(TEST_DISCOUNT_V1)
      .startAt(LocalDate.parse(TEST_START_AT_V1))
      .endAt(LocalDate.parse(TEST_END_AT_V1))
      .build();

  Page<Coupon> TEST_COUPONS = new PageImpl<>(List.of(Coupon.of(TEST_COUPON_CREATE_REQUEST_DTO)));

  CouponIssuance TEST_COUPON_ISSUANCE = CouponIssuance.of(TEST_COUPON_V1, TEST_USER);

  CouponIssuanceCreateRequestDto TEST_COUPON_ISSUANCE_CREATE_REQUEST_DTO =
      new CouponIssuanceCreateRequestDto(TEST_COUPON_ID, TEST_COUPON_USER_ID);

}
