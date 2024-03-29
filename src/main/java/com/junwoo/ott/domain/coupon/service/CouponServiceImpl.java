package com.junwoo.ott.domain.coupon.service;

import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponCreateResponseDto;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.domain.coupon.repository.CouponIssuanceRepository;
import com.junwoo.ott.domain.coupon.repository.CouponRepository;
import com.junwoo.ott.global.customenum.CouponType;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.exception.custom.CustomTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class CouponServiceImpl implements CouponService {

  private final CouponRepository couponRepository;
  private final CouponIssuanceRepository couponIssuanceRepository;

  @Override
  public CouponCreateResponseDto createCoupon(CouponCreateRequestDto createRequestDto) {
    // 회원에 대한 검증

    checkCouponMembershipType(createRequestDto.getMembershipType());
    checkCouponType(createRequestDto.getType());

    Coupon coupon = Coupon.of(createRequestDto);
    Coupon savedCoupon = couponRepository.save(coupon);

    return new CouponCreateResponseDto(savedCoupon);
  }

  private void checkCouponType(String couponType) {
    for (CouponType type : CouponType.values()) {
      if (type.getMessage().equals(couponType)) {
        return;
      }
    }

    throw new CustomTypeException("해당 쿠폰 등급이 존재하지 않습니다.");
  }

  private void checkCouponMembershipType(String membershipType) {
    for (MembershipType type : MembershipType.values()) {
      if (type.getGrade().equals(membershipType)) {
        return;
      }
    }

    throw new CustomTypeException("해당 멤버쉽 등급이 존재하지 않습니다.");
  }

}
