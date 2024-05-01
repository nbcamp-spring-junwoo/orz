package com.junwoo.ott.domain.coupon.dto.response;

import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class CouponIssuanceCreateResponseDto {

  private final Long couponIssuanceId;
  private final Coupon coupon;
  private final User user;
  private final LocalDateTime issuedAt;
  private final LocalDateTime usedAt;

  public CouponIssuanceCreateResponseDto(final CouponIssuance couponIssuance) {
    couponIssuanceId = couponIssuance.getCouponIssuanceId();
    coupon = couponIssuance.getCoupon();
    user = couponIssuance.getUser();
    issuedAt = couponIssuance.getIssuedAt();
    usedAt = couponIssuance.getUsedAt();
  }

}
