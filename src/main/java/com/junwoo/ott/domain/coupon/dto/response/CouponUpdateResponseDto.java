package com.junwoo.ott.domain.coupon.dto.response;

import com.junwoo.ott.domain.coupon.entity.Coupon;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponUpdateResponseDto {

  private Coupon coupon;

}
