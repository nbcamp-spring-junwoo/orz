package com.junwoo.ott.domain.coupon.dto.body;

import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.global.customenum.CouponType;
import com.junwoo.ott.global.customenum.MembershipType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class CouponCreateDto {

  private final String description;
  @NotNull
  private final CouponType type;
  @NotNull
  private final MembershipType membershipType;
  @NotNull
  private final Integer discount;
  @NotNull
  private final Integer count;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private final String startAt;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private final String endAt;

  public CouponCreateDto(String description, CouponType type, MembershipType membershipType,
      Integer discount, Integer count, String startAt, String endAt) {
    Coupon.validateDateRange(startAt, endAt);
    this.description = description;
    this.type = type;
    this.membershipType = membershipType;
    this.discount = discount;
    this.count = count;
    this.startAt = startAt;
    this.endAt = endAt;
  }

}
