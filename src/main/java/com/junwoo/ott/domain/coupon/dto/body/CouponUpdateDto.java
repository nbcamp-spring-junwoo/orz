package com.junwoo.ott.domain.coupon.dto.body;

import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.global.customenum.CouponType;
import com.junwoo.ott.global.customenum.MembershipType;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class CouponUpdateDto {

  private final String description;
  private final CouponType type;
  private final MembershipType membershipType;
  private final Integer discount;
  private final Integer count;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private final String startAt;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private final String endAt;

  public CouponUpdateDto(String description, CouponType type, MembershipType membershipType,
      Integer discount, Integer count, String startAt, String endAt) {
    validateDate(startAt, endAt);
    this.description = description;
    this.type = type;
    this.membershipType = membershipType;
    this.discount = discount;
    this.count = count;
    this.startAt = startAt;
    this.endAt = endAt;
  }

  public void validateDate(String startAt, String endAt) {
    if (startAt == null || endAt == null) {
      return;
    }

    Coupon.validateDateRange(startAt, endAt);

  }

}
