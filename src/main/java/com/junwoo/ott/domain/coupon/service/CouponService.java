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
import com.junwoo.ott.domain.user.dto.reponse.UserReadResponseDto;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.aop.Lockable;
import jakarta.persistence.EntityNotFoundException;
import java.util.concurrent.TimeUnit;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CouponService {

  private final UserService userService;

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
        coupon -> new CouponIssuanceReadResponseDto(coupon.getCoupon(), coupon));
  }

  public CouponCreateResponseDto createCoupon(final CouponCreateRequestDto createRequestDto) {
    Coupon coupon = Coupon.of(createRequestDto);
    Coupon savedCoupon = couponRepository.save(coupon);

    return new CouponCreateResponseDto(savedCoupon);
  }

  @Lockable(value = "createCouponIssuance Lock", waitTime = 50, leaseTime = 50, timeUnit = TimeUnit.SECONDS)
  @Transactional(isolation = Isolation.SERIALIZABLE)
  public CouponIssuanceCreateResponseDto createCouponIssuance(
      final CouponIssuanceCreateRequestDto createRequestDto
  ) {
    Coupon coupon = existCouponById(createRequestDto.getCouponId());
    UserReadResponseDto dto = userService.getUser(createRequestDto.getUserId());
    User user = createUser(dto);
    coupon.decreaseCount();

    CouponIssuance couponIssuance = CouponIssuance.of(coupon, user);

    couponRepository.saveAndFlush(coupon);
    couponIssuanceRepository.saveAndFlush(couponIssuance);

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

  @Transactional(readOnly = true)
  protected Coupon existCouponById(final Long couponId) {

    return couponRepository.findById(couponId).orElseThrow(
        () -> new EntityNotFoundException("해당 쿠폰이 존재하지 않습니다.")
    );
  }

  private User createUser(final UserReadResponseDto dto) {

    return User.builder()
        .userId(dto.getUserId())
        .username(dto.getUsername())
        .email(dto.getEmail())
        .born(dto.getBorn())
        .authorityType(dto.getAuthorityType())
        .membershipType(dto.getMembershipType())
        .build();
  }

}
