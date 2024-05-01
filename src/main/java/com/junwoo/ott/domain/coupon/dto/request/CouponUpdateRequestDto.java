package com.junwoo.ott.domain.coupon.dto.request;

import com.junwoo.ott.domain.coupon.dto.body.CouponUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponUpdateRequestDto {

  private Long couponId;
  private CouponUpdateDto dto;

}
