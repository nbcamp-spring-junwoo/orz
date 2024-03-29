package com.junwoo.ott.domain.coupon.test;

import com.junwoo.ott.domain.coupon.dto.body.CouponCreateDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponReadRequestDto;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.global.customenum.CouponType;
import com.junwoo.ott.global.customenum.MembershipType;
import java.time.LocalDate;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

public interface CouponTestValues {

  Long TEST_MEMBER_ID = 1L;
  Long TEST_COUPON_ID = 1L;
  Integer TEST_PAGE = 1;
  Integer TEST_SIZE = 3;
  Integer TEST_COUPON_SIZE = 1;
  String TEST_DESCRIPTION = "테스트 설명";
  CouponType TEST_COUPON_TYPE = CouponType.FIX;
  MembershipType TEST_MEMBERSHIP_TYPE = MembershipType.SILVER;
  Integer TEST_DISCOUNT = 10;
  Integer TEST_COUNT = 100;
  String TEST_START_AT = "2024-04-01";
  String TEST_END_AT = "2024-04-30";

  CouponCreateDto TEST_COUPON_CREATE_DTO = new CouponCreateDto(
      TEST_DESCRIPTION,
      TEST_COUPON_TYPE,
      TEST_MEMBERSHIP_TYPE,
      TEST_DISCOUNT,
      TEST_COUNT,
      TEST_START_AT,
      TEST_END_AT
  );

  CouponCreateRequestDto TEST_COUPON_CREATE_REQUEST_DTO = new CouponCreateRequestDto(
      TEST_MEMBER_ID,
      TEST_COUPON_CREATE_DTO
  );

  CouponReadRequestDto TEST_COUPON_READ_REQUEST_DTO = new CouponReadRequestDto(
      TEST_PAGE,
      TEST_SIZE
  );

  Coupon TEST_COUPON_V1 = Coupon.of(TEST_COUPON_CREATE_REQUEST_DTO);

  Coupon TEST_COUPON_V2 = Coupon.builder()
      .couponId(TEST_COUPON_ID)
      .description(TEST_DESCRIPTION)
      .couponType(TEST_COUPON_TYPE)
      .membershipType(TEST_MEMBERSHIP_TYPE)
      .count(TEST_COUNT)
      .discount(TEST_DISCOUNT)
      .startAt(LocalDate.parse(TEST_START_AT))
      .endAt(LocalDate.parse(TEST_END_AT))
      .build();

  Page<Coupon> TEST_COUPONS = new PageImpl<>(List.of(Coupon.of(TEST_COUPON_CREATE_REQUEST_DTO)));

}
