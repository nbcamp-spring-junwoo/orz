package com.junwoo.ott.domain.payment.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.times;

import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.service.CouponService;
import com.junwoo.ott.domain.coupon.test.CouponTestValues;
import com.junwoo.ott.domain.payment.dto.response.OrderResponseDto;
import com.junwoo.ott.domain.payment.repository.OrderItemRepository;
import com.junwoo.ott.domain.payment.repository.OrderRepository;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.domain.subscription.test.SubscriptionServiceTestValues;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest implements
    SubscriptionServiceTestValues, CouponTestValues {

  @Mock
  private CouponService couponService;
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private OrderItemRepository orderItemRepository;
  @InjectMocks
  private OrderService orderService;

  @Nested
  class createSubscriptionOrder {

    @DisplayName("성공: 쿠폰이 있는 경우")
    @Test
    void shouldCreateSubscriptionOrderSuccessfullyWithCoupon() {
      // given
      Subscription subscription = TEST_SUBSCRIPTION;
      CouponIssuanceReadResponseDto couponIssuance = new CouponIssuanceReadResponseDto(TEST_COUPON_V1, TEST_COUPON_ISSUANCE);

      given(couponService.getCouponIssuance(any())).willReturn(couponIssuance);
      given(orderRepository.save(any())).willAnswer((invocation) -> invocation.getArgument(0));

      // when
      OrderResponseDto result = orderService.createSubscriptionOrder(subscription, 1L);

      // then
      assertNotNull(result);
      then(orderRepository).should(times(1)).save(any());
      then(orderItemRepository).should(times(1)).save(any());
    }

    @DisplayName("성공: 쿠폰이 없는 경우")
    @Test
    void shouldCreateSubscriptionOrderSuccessfullyWithoutCoupon() {
      // given
      Subscription subscription = TEST_SUBSCRIPTION;

      given(orderRepository.save(any())).willAnswer((invocation) -> invocation.getArgument(0));

      // when
      OrderResponseDto result = orderService.createSubscriptionOrder(subscription, null);

      // then
      assertNotNull(result);
      then(orderRepository).should(times(1)).save(any());
      then(orderItemRepository).should(times(1)).save(any());
    }

    @DisplayName("실패")
    @Test
    void shouldThrowExceptionWhenCouponIssuanceIdIsInvalid() {
      // given
      Subscription subscription = TEST_SUBSCRIPTION;
      Long couponIssuanceId = 1L;

      given(couponService.getCouponIssuance(couponIssuanceId)).willReturn(null);

      // when & then
      assertThrows(RuntimeException.class, () -> orderService.createSubscriptionOrder(subscription, couponIssuanceId));
    }

  }

}
