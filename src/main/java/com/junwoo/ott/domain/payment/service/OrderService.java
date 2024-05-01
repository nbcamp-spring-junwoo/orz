package com.junwoo.ott.domain.payment.service;

import static com.junwoo.ott.global.customenum.payment.OrderType.SUBSCRIPTION;

import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.service.CouponService;
import com.junwoo.ott.domain.payment.dto.request.OrderReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.OrderReadResponseDto;
import com.junwoo.ott.domain.payment.dto.response.OrderResponseDto;
import com.junwoo.ott.domain.payment.entity.Order;
import com.junwoo.ott.domain.payment.entity.OrderItem;
import com.junwoo.ott.domain.payment.repository.OrderItemRepository;
import com.junwoo.ott.domain.payment.repository.OrderRepository;
import com.junwoo.ott.domain.payment.util.PaymentUtil;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.global.exception.custom.UserNotSameException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class OrderService {

  private final CouponService couponService;
  private final OrderRepository orderRepository;
  private final OrderItemRepository orderItemRepository;

  public OrderReadResponseDto readOrder(final OrderReadRequestDto requestDto) {
    Order order = orderRepository.findByOrderId(requestDto.getOrderId())
        .orElseThrow(() -> new IllegalArgumentException("주문을 찾을 수 없습니다."));

    if (!order.getUser().getUserId().equals(requestDto.getUserId())) {
      throw new UserNotSameException("주문을 찾을 수 없습니다.");
    }

    return OrderReadResponseDto.of(order);
  }

  public OrderResponseDto createSubscriptionOrder(
      final Subscription subscription, final Long couponIssuanceId
  ) {
    CouponIssuanceReadResponseDto couponIssuance = null;
    if (couponIssuanceId != null) {
      couponIssuance = couponService.getCouponIssuance(couponIssuanceId);
      couponService.useCouponIssuance(couponIssuanceId);
    }

    Order order = Order.builder()
        .orderId(PaymentUtil.generateOrderId(SUBSCRIPTION))
        .user(subscription.getUser())
        .build();

    OrderItem orderItem = OrderItem.ofSubscription(subscription, couponIssuance);
    orderItem.setParents(order);

    order.setOrderName();
    orderRepository.saveAndFlush(order);
    orderItemRepository.saveAndFlush(orderItem);

    return order.toResponseDto();
  }

}
