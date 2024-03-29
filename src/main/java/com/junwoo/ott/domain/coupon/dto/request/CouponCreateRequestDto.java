package com.junwoo.ott.domain.coupon.dto.request;

import com.junwoo.ott.domain.coupon.dto.body.CouponCreateDto;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class CouponCreateRequestDto {

  private final Long memberId;
  private final String description;
  private final String type;
  private final String membershipType;
  private final Integer discount;
  private final Integer count;
  private final LocalDate startAt;
  private final LocalDate endAt;

  public CouponCreateRequestDto(Long memberId, CouponCreateDto dto) {
    this.memberId = memberId;
    this.description = dto.getDescription();
    this.type = dto.getType();
    this.membershipType = dto.getMembershipType();
    this.discount = dto.getDiscount();
    this.count = dto.getCount();
    this.startAt = LocalDate.parse(dto.getStartAt());
    this.endAt = LocalDate.parse(dto.getEndAt());
  }

}
