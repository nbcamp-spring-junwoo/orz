package com.junwoo.ott.domain.coupon.service;

import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponReadRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponCreateResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponReadResponseDto;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.domain.coupon.repository.CouponIssuanceRepository;
import com.junwoo.ott.domain.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponService {

  private final CouponRepository couponRepository;
  private final CouponIssuanceRepository couponIssuanceRepository;

  public Page<CouponReadResponseDto> getCoupon(CouponReadRequestDto dto) {
    // 관리자에 대한 검증

    Page<Coupon> couponList = couponRepository.getCoupons(dto.getPageable());

    return couponList.map(CouponReadResponseDto::new);
  }

  public Page<CouponIssuanceReadResponseDto> getCoupons(CouponReadRequestDto dto) {
    // 회원에 대한 검증

    Page<CouponIssuance> couponIssuanceList = couponIssuanceRepository.getCouponIssuance(
        dto.getUserId(), dto.getPageable());

    return couponIssuanceList.map(
        coupon -> new CouponIssuanceReadResponseDto(coupon.getCoupon(), coupon));
  }

  public CouponCreateResponseDto createCoupon(CouponCreateRequestDto createRequestDto) {
    // 회원에 대한 검증

    Coupon coupon = Coupon.of(createRequestDto);
    Coupon savedCoupon = couponRepository.save(coupon);

    return new CouponCreateResponseDto(savedCoupon);
  }

}
