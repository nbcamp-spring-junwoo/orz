package com.junwoo.ott.global.aop;

import com.junwoo.ott.domain.auth.dto.body.AuthSignupDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponIssuanceCreateRequestDto;
import com.junwoo.ott.domain.coupon.service.CouponService;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.repository.UserRepository;
import com.junwoo.ott.global.exception.custom.UserNotFoundException;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j(topic = "CouponIssuanceAspect")
@Aspect
@AllArgsConstructor
@Component
public class CouponIssuanceAspect {

  private final CouponService couponService;

  private final UserRepository userRepository;

  @AfterReturning(value = "@annotation(couponIssuance) && args(dto)")
  public void addCouponIssuance(CouponIssuance couponIssuance, AuthSignupDto dto) {
    User user = userRepository.findByUsername(dto.getUsername())
        .orElseThrow(() -> new UserNotFoundException("해당 유저가 존재하지 않습니다."));

    couponService.createCouponIssuance(
        new CouponIssuanceCreateRequestDto(couponIssuance.couponId(), user.getUserId())
    );

    log.info("생성된 유저의 아이디 ID : {} | 발급 쿠폰 아이디 : {}", user.getUserId(), couponIssuance.couponId());
  }

}
