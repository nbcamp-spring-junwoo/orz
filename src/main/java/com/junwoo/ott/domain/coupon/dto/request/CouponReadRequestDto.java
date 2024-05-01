package com.junwoo.ott.domain.coupon.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class CouponReadRequestDto {

  private Long userId;
  private Pageable pageable;

  public CouponReadRequestDto(final Pageable pageable) {
    this.pageable = pageable;
  }

}
