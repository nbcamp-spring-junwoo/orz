package com.junwoo.ott.domain.coupon.dto.response;

import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.global.customenum.CouponType;
import com.junwoo.ott.global.customenum.MembershipType;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CouponReadResponseDto {

  private final Long couponId;
  private final CouponType couponType;
  private final MembershipType membershipType;
  private final Integer discount;
  private final String description;
  private final LocalDate startAt;
  private final LocalDate endAt;

  public CouponReadResponseDto(final Coupon coupon) {
    this.couponId = coupon.getCouponId();
    this.couponType = coupon.getCouponType();
    this.membershipType = coupon.getMembershipType();
    this.discount = coupon.getDiscount();
    this.description = coupon.getDescription();
    this.startAt = coupon.getStartAt();
    this.endAt = coupon.getEndAt();
  }

}
