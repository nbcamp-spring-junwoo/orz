package com.junwoo.ott.domain.coupon.controller;

import com.junwoo.ott.domain.coupon.dto.body.CouponCreateDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponCreateResponseDto;
import com.junwoo.ott.domain.coupon.service.CouponService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/coupons")
@RequiredArgsConstructor
@RestController
public class CouponController {

  private final CouponService couponService;

  public ResponseDto<Void> getCoupon() {
    return null;
  }

  @PostMapping
  public ResponseDto<CouponCreateResponseDto> postCoupon(
      @Validated @RequestBody CouponCreateDto dto
  ) {
    CouponCreateRequestDto createRequestDto = new CouponCreateRequestDto(1L, dto);
    CouponCreateResponseDto responseDto = couponService.createCoupon(createRequestDto);

    return ResponseDto.ok(responseDto);
  }

  public ResponseDto<Void> putCoupon() {
    return null;
  }

  public ResponseDto<Void> deleteCoupon() {
    return null;
  }

}
