package com.junwoo.ott.domain.coupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CouponReadRequestDto {

  private Long userId;
  private Integer page;
  private Integer size;

  public CouponReadRequestDto(Integer page, Integer size) {
    this.page = page;
    this.size = size;
  }

}
