package com.junwoo.ott.domain.coupon.service;

import com.junwoo.ott.domain.auth.dto.body.AuthSignupDto;
import com.junwoo.ott.domain.auth.dto.request.AuthSignupRequestDto;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.domain.coupon.repository.CouponRepository;
import com.junwoo.ott.domain.coupon.test.CouponTestValues;
import com.junwoo.ott.domain.coupon.test.CouponUserTestValues;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.exception.custom.CustomCouponException;
import jakarta.annotation.PostConstruct;
import java.util.stream.LongStream;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

@Disabled
@SpringBootTest
public class CouponConcurrencyTest implements CouponTestValues, CouponUserTestValues {

  @Autowired
  private CouponService couponService;
  @Autowired
  private UserService userService;
  @Autowired
  private CouponRepository couponRepository;

  private final User user = TEST_USER;

  @PostConstruct
  void setUp() {
    userService.createUser(new AuthSignupRequestDto
        (new AuthSignupDto(user.getUsername(), user.getPassword(), user.getEmail(), "1997-03-15"))
    );
    couponService.createCoupon(TEST_COUPON_CREATE_REQUEST_DTO);
  }

  @Test
  @DisplayName("동시성 문제 테스트")
  @Rollback(value = false)
  void 테스트() {
    Coupon coupon1 = couponRepository.findById(TEST_COUPON_ID).orElseThrow();
    System.out.println("현재 쿠폰의 개수 : " + coupon1.getCount());

    LongStream.range(0, TEST_COUNT_RANGE).parallel().forEach(
        i -> {
          try {
            couponService.createCouponIssuance(TEST_COUPON_ISSUANCE_CREATE_REQUEST_DTO);

          } catch (CustomCouponException e) {
            System.out.println(e.getMessage());
            Coupon coupon = couponRepository.findById(TEST_COUPON_ID).orElseThrow();
            System.out.println("현재 쿠폰의 남은 개수 : " + coupon.getCount());
          }
        }
    );
    Coupon coupon2 = couponRepository.findById(TEST_COUPON_ID).orElseThrow();
    System.out.println("현재 쿠폰의 남은 개수 : " + coupon2.getCount());

    if (TEST_COUNT_V1 <= TEST_COUNT_RANGE) {
      Assertions.assertEquals(coupon2.getCount(), 0);
    } else {
      Assertions.assertEquals(coupon2.getCount(), TEST_COUNT_V1 - TEST_COUNT_RANGE);
    }

  }

}
