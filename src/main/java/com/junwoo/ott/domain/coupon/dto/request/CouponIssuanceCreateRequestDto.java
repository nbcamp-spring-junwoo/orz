package com.junwoo.ott.domain.coupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponIssuanceCreateRequestDto {

  private Long couponId;
  private Long userId;

}
