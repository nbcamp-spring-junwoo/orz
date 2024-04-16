package com.junwoo.ott.domain.coupon.controller;

import com.junwoo.ott.domain.coupon.dto.body.CouponCreateDto;
import com.junwoo.ott.domain.coupon.dto.body.CouponUpdateDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponReadRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponUpdateRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponReadResponseDto;
import com.junwoo.ott.domain.coupon.service.CouponService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
@RequestMapping("/api/v1/admin/coupons")
@RestController
public class CouponAdminController {

  private final CouponService couponService;

  @GetMapping
  public ResponseDto<Page<CouponReadResponseDto>> getCoupons(
      final Pageable pageable
  ) {
    CouponReadRequestDto dto = new CouponReadRequestDto(pageable);
    Page<CouponReadResponseDto> result = couponService.getCoupon(dto);

    return ResponseDto.ok(result);
  }

  @PostMapping
  public void postCoupon(
      final @Validated @RequestBody CouponCreateDto dto
  ) {
    CouponCreateRequestDto createRequestDto = new CouponCreateRequestDto(dto);
    couponService.createCoupon(createRequestDto);
  }

  @PutMapping("/{couponId}")
  public void putCoupon(
      final @PathVariable("couponId") Long couponId,
      final @RequestBody CouponUpdateDto dto
  ) {
    CouponUpdateRequestDto couponUpdateRequestDto = new CouponUpdateRequestDto(couponId, dto);
    couponService.updateCoupon(couponUpdateRequestDto);
  }

  @DeleteMapping("/{couponId}")
  public void deleteCoupon(
      @PathVariable("couponId") final Long couponId
  ) {
    couponService.deleteCoupon(couponId);
  }

}
