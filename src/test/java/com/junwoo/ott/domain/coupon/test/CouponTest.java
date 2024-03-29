package com.junwoo.ott.domain.coupon.test;

import com.junwoo.ott.domain.coupon.dto.body.CouponCreateDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;

public interface CouponTest {

  Long TEST_MEMBER_ID = 1L;
  Long TEST_COUPON_ID = 1L;
  String TEST_DESCRIPTION = "테스트 설명";
  String TEST_COUPON_TYPE = "FIX";
  String TEST_MEMBERSHIP_TYPE = "SILVER";
  Integer TEST_DISCOUNT = 10;
  Integer TEST_COUNT = 100;
  String TEST_START_AT = "2024-04-01";
  String TEST_END_AT = "2024-04-30";

  String TEST_COUPON_ERROR_TYPE = "TEST";
  String TEST_MEMBERSHIP_ERROR_TYPE = "TEST";

  CouponCreateDto TEST_COUPON_CREATE_DTO = new CouponCreateDto(
      TEST_DESCRIPTION,
      TEST_COUPON_TYPE,
      TEST_MEMBERSHIP_TYPE,
      TEST_DISCOUNT,
      TEST_COUNT,
      TEST_START_AT,
      TEST_END_AT
  );

  CouponCreateDto TEST_COUPON_COUPON_TYPE_ERROR_DTO = new CouponCreateDto(
      TEST_DESCRIPTION,
      TEST_COUPON_ERROR_TYPE,
      TEST_MEMBERSHIP_TYPE,
      TEST_DISCOUNT,
      TEST_COUNT,
      TEST_START_AT,
      TEST_END_AT
  );

  CouponCreateDto TEST_MEMBERSHIP_TYPE_ERROR_DTO = new CouponCreateDto(
      TEST_DESCRIPTION,
      TEST_COUPON_TYPE,
      TEST_MEMBERSHIP_ERROR_TYPE,
      TEST_DISCOUNT,
      TEST_COUNT,
      TEST_START_AT,
      TEST_END_AT
  );

  CouponCreateRequestDto TEST_COUPON_CREATE_REQUEST_DTO = new CouponCreateRequestDto(
      TEST_MEMBER_ID,
      TEST_COUPON_CREATE_DTO
  );

}
