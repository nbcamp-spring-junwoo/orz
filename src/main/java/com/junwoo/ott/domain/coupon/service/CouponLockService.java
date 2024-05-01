package com.junwoo.ott.domain.coupon.service;

import com.junwoo.ott.domain.coupon.dto.request.CouponIssuanceCreateRequestDto;
import com.junwoo.ott.global.aop.Lockable;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CouponLockService {

  private final CouponService couponService;

  @Lockable(value = "createCouponIssuance Lock", waitTime = 5, leaseTime = 3, timeUnit = TimeUnit.SECONDS)
  public void createCouponIssuanceLock(final CouponIssuanceCreateRequestDto createRequestDto) {
    couponService.createCouponIssuance(createRequestDto);
  }

}
