package com.junwoo.ott.domain.payment.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.service.CouponService;
import com.junwoo.ott.domain.coupon.test.CouponTestValues;
import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.domain.payment.entity.Order;
import com.junwoo.ott.domain.payment.repository.OrderItemRepository;
import com.junwoo.ott.domain.payment.repository.OrderRepository;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest implements OrderServiceTestValues, CouponTestValues {

  @InjectMocks
  private OrderService orderService;
  @Mock
  private CouponService couponService;
  @Mock
  private OrderRepository orderRepository;
  @Mock
  private OrderItemRepository orderItemRepository;

  FixtureMonkey fx = FixtureMonkey.builder()
      .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
      .build();


  @DisplayName("createSubscriptionOrder")
  @Nested
  class createSubscriptionOrder {

    @DisplayName("성공: 쿠폰 없음")
    @Test
    void success_without_coupon() {
      // given
      Membership membership = fx.giveMeBuilder(Membership.class)
          .setNotNull("*")
          .sample();
      Subscription subscription = fx.giveMeBuilder(Subscription.class)
          .set("membership", membership)
          .sample();
      Long couponIssuanceId = 1L;
      Order savedOrder = fx.giveMeBuilder(Order.class)
          .set("orderItems", List.of(TEST_ORDER_ITEM))
          .sample();

      given(orderRepository.save(any())).willReturn(savedOrder);

      // when
      orderService.createSubscriptionOrder(subscription, couponIssuanceId);

      // then
      then(orderRepository).should().save(any());
      then(orderItemRepository).should().save(any());
    }

    @DisplayName("성공: 쿠폰 있음")
    @Test
    void success_with_coupon() {
      // given
      Membership membership = fx.giveMeBuilder(Membership.class)
          .setNotNull("*")
          .sample();
      Subscription subscription = fx.giveMeBuilder(Subscription.class)
          .set("membership", membership)
          .sample();
      Long couponIssuanceId = 1L;
      Order savedOrder = fx.giveMeBuilder(Order.class)
          .set("orderItems", List.of(TEST_ORDER_ITEM))
          .sample();

      given(orderRepository.save(any())).willReturn(savedOrder);
      given(couponService.getCouponIssuance(couponIssuanceId)).willReturn(new CouponIssuanceReadResponseDto(TEST_COUPON_V1, TEST_COUPON_ISSUANCE));

      // when
      orderService.createSubscriptionOrder(subscription, couponIssuanceId);

      // then
      then(orderRepository).should().save(any());
      then(orderItemRepository).should().save(any());
    }

  }

}
