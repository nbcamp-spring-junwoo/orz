package com.junwoo.ott.domain.payment.service;

import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.service.CouponService;
import com.junwoo.ott.domain.payment.dto.response.OrderResponseDto;
import com.junwoo.ott.domain.payment.entity.Order;
import com.junwoo.ott.domain.payment.entity.OrderItem;
import com.junwoo.ott.domain.payment.repository.OrderItemRepository;
import com.junwoo.ott.domain.payment.repository.OrderRepository;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.global.customenum.payment.OrderType;
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

  public OrderResponseDto createSubscriptionOrder(
      final Subscription subscription, final Long couponIssuanceId
  ) {
    CouponIssuanceReadResponseDto couponIssuance = null;
    if (couponIssuanceId != null) {
      couponIssuance = couponService.getCouponIssuance(couponIssuanceId);
    }

    Order order = new Order();

    Integer totalPrice = subscription.getMembership().getPrice();
    boolean isCouponUsable = (couponIssuanceId != null) && (couponIssuance != null);
    OrderItem orderItem = OrderItem.builder()
        .itemCode(OrderType.SUBSCRIPTION.getCode() + subscription.getMembership().getMembershipId())
        .basePrice(totalPrice)
        .discountingPrice(isCouponUsable
            ? calculateDiscountingPrice(totalPrice, couponIssuance)
            : 0)
        .build();
    orderItem.setParents(order);

    order.setParents(subscription.getUser());

    Order savedOrder = orderRepository.save(order);
    orderItemRepository.save(orderItem);

    return savedOrder.toResponseDto();
  }

  private Integer calculateDiscountingPrice(
      final Integer totalPrice, final CouponIssuanceReadResponseDto couponIssuance
  ) {
    return switch (couponIssuance.getCouponType()) {
      case FIX -> Math.min(couponIssuance.getDiscount(), totalPrice);
      case RATIO -> totalPrice * couponIssuance.getDiscount() / 100;
    };
  }

}
