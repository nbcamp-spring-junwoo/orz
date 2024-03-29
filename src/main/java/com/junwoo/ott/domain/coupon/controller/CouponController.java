package com.junwoo.ott.domain.coupon.controller;

import com.junwoo.ott.domain.coupon.dto.body.CouponCreateDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponReadRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponReadResponseDto;
import com.junwoo.ott.domain.coupon.service.CouponService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CouponController {

  private final CouponService couponService;

  @GetMapping("/coupons")
  public ResponseDto<Page<CouponReadResponseDto>> getCoupons(Pageable pageable) {
    CouponReadRequestDto dto = new CouponReadRequestDto(pageable.getPageNumber(),
        pageable.getPageSize());
    Page<CouponReadResponseDto> result = couponService.getCoupon(dto);

    return ResponseDto.ok(result);
  }

  @GetMapping("users/{userId}/coupons")
  public ResponseDto<Page<CouponIssuanceReadResponseDto>> getCoupons(
      @PathVariable("userId") Long userId,
      Pageable pageable
  ) {
    CouponReadRequestDto dto = new CouponReadRequestDto(userId, pageable.getPageNumber(),
        pageable.getPageSize());
    Page<CouponIssuanceReadResponseDto> result = couponService.getCoupons(dto);

    return ResponseDto.ok(result);
  }

  @PostMapping("/coupons")
  public void postCoupon(
      @Validated @RequestBody CouponCreateDto dto
  ) {
    CouponCreateRequestDto createRequestDto = new CouponCreateRequestDto(1L, dto);
    couponService.createCoupon(createRequestDto);
  }

  public ResponseDto<Void> putCoupon() {
    return null;
  }

  public ResponseDto<Void> deleteCoupon() {
    return null;
  }

}
