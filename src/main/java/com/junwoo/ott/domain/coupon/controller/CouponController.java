package com.junwoo.ott.domain.coupon.controller;

import com.junwoo.ott.domain.coupon.dto.body.CouponCreateDto;
import com.junwoo.ott.domain.coupon.dto.body.CouponUpdateDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponIssuanceCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponReadRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponUpdateRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponReadResponseDto;
import com.junwoo.ott.domain.coupon.service.CouponService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CouponController {

  private final CouponService couponService;

  @Secured(value = "ROLE_ADMIN")
  @GetMapping("/coupons")
  public ResponseDto<Page<CouponReadResponseDto>> getCoupons(
      final Pageable pageable
  ) {
    CouponReadRequestDto dto = new CouponReadRequestDto(pageable);
    Page<CouponReadResponseDto> result = couponService.getCoupon(dto);

    return ResponseDto.ok(result);
  }

  @GetMapping("users/{userId}/coupons")
  public ResponseDto<Page<CouponIssuanceReadResponseDto>> getCoupons(
      final @PathVariable("userId") Long userId,
      final Pageable pageable
  ) {
    CouponReadRequestDto dto = new CouponReadRequestDto(userId, pageable);
    Page<CouponIssuanceReadResponseDto> result = couponService.getCoupons(dto);

    return ResponseDto.ok(result);
  }

  @Secured(value = "ROLE_ADMIN")
  @PostMapping("/coupons")
  public void postCoupon(
      final @Validated @RequestBody CouponCreateDto dto
  ) {
    CouponCreateRequestDto createRequestDto = new CouponCreateRequestDto(dto);
    couponService.createCoupon(createRequestDto);
  }

  @PostMapping("/coupons/get-coupon/{couponId}")
  public void postCouponIssuance(
      final @PathVariable("couponId") Long couponId,
      final @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    CouponIssuanceCreateRequestDto createRequestDto = new CouponIssuanceCreateRequestDto(
        couponId, userDetails.getUserId());
    couponService.createCouponIssuance(createRequestDto);
  }

  @Secured(value = "ROLE_ADMIN")
  @PutMapping("/coupons/{couponId}")
  public void putCoupon(
      final @PathVariable("couponId") Long couponId,
      final @RequestBody CouponUpdateDto dto
  ) {
    CouponUpdateRequestDto couponUpdateRequestDto = new CouponUpdateRequestDto(couponId, dto);
    couponService.updateCoupon(couponUpdateRequestDto);
  }

  @Secured(value = "ROLE_ADMIN")
  @DeleteMapping("/coupons/{couponId}")
  public void deleteCoupon(
      @PathVariable("couponId") Long couponId
  ) {
    couponService.deleteCoupon(couponId);
  }

}
