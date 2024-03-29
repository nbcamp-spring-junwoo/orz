package com.junwoo.ott.domain.coupon.service;

import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponCreateResponseDto;

public interface CouponService {

  CouponCreateResponseDto createCoupon(CouponCreateRequestDto dto);

}
