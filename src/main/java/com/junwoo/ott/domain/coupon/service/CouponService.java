package com.junwoo.ott.domain.coupon.service;

import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponIssuanceCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponReadRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponUpdateRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponCreateResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceCreateResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponReadResponseDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponUpdateResponseDto;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.domain.coupon.repository.CouponIssuanceRepository;
import com.junwoo.ott.domain.coupon.repository.CouponRepository;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.exception.custom.CustomCouponException;
import jakarta.persistence.EntityNotFoundException;
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

  @Transactional(readOnly = true)
  public Page<CouponReadResponseDto> getCoupon(final CouponReadRequestDto dto) {
    Page<Coupon> couponList = couponRepository.findAllByOrderByStartAtDesc(dto.getPageable());

    return couponList.map(CouponReadResponseDto::new);
  }

  @Transactional(readOnly = true)
  public CouponIssuanceReadResponseDto getCouponIssuance(final Long couponIssuanceId) {
    CouponIssuance couponIssuance = couponIssuanceRepository.findById(couponIssuanceId)
        .orElseThrow(() -> new EntityNotFoundException("해당 쿠폰이 존재하지 않습니다."));

    return new CouponIssuanceReadResponseDto(couponIssuance.getCoupon(), couponIssuance);
  }

  @Transactional(readOnly = true)
  public Page<CouponIssuanceReadResponseDto> getCoupons(final CouponReadRequestDto dto) {
    Page<CouponIssuance> couponIssuanceList = couponIssuanceRepository.getCouponIssuance(
        dto.getUserId(), dto.getPageable());

    return couponIssuanceList.map(
        couponIssuance -> new CouponIssuanceReadResponseDto(couponIssuance.getCoupon(),
            couponIssuance));
  }

  public CouponCreateResponseDto createCoupon(final CouponCreateRequestDto createRequestDto) {
    Coupon coupon = Coupon.of(createRequestDto);
    Coupon savedCoupon = couponRepository.save(coupon);

    return new CouponCreateResponseDto(savedCoupon);
  }

  public CouponIssuanceCreateResponseDto createCouponIssuance(
      final CouponIssuanceCreateRequestDto createRequestDto
  ) {
    Coupon coupon = existCouponById(createRequestDto.getCouponId());
    User user = User.builder().userId(createRequestDto.getUserId()).build();

    if (couponIssuanceRepository.existsCouponIssuanceByCoupon_CouponIdAndUser_UserId(
        coupon.getCouponId(), user.getUserId())) {
      throw new CustomCouponException("해당 쿠폰은 이미 발행 되었습니다.");
    }

    coupon.decreaseCount();

    CouponIssuance couponIssuance = CouponIssuance.of(coupon, user);

    couponRepository.save(coupon);
    couponIssuanceRepository.save(couponIssuance);

    return new CouponIssuanceCreateResponseDto(couponIssuance);
  }

  public void deleteCoupon(final Long couponId) {
    Coupon coupon = existCouponById(couponId);

    couponRepository.delete(coupon);
  }

  public CouponUpdateResponseDto updateCoupon(final CouponUpdateRequestDto couponUpdateRequestDto) {
    Coupon coupon = existCouponById(couponUpdateRequestDto.getCouponId());
    coupon.updateCoupon(couponUpdateRequestDto.getDto());

    return new CouponUpdateResponseDto(coupon);
  }

  public void useCouponIssuance(final Long couponIssuanceId) {
    CouponIssuance couponIssuance = couponIssuanceRepository.findById(couponIssuanceId)
        .orElseThrow(() -> new EntityNotFoundException("발급된 쿠폰이 없습니다."));

    couponIssuanceRepository.delete(couponIssuance);
  }

  @Transactional(readOnly = true)
  public Coupon existCouponById(final Long couponId) {

    return couponRepository.findById(couponId).orElseThrow(
        () -> new EntityNotFoundException("해당 쿠폰이 존재하지 않습니다.")
    );
  }

}
