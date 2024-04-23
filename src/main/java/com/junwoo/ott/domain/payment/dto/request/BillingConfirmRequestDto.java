package com.junwoo.ott.domain.payment.dto.request;

import com.junwoo.ott.domain.payment.dto.response.OrderItemResponseDto;
import com.junwoo.ott.domain.payment.dto.response.OrderResponseDto;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BillingConfirmRequestDto {

  private Integer amount;
  private String customerKey;
  private String orderId;
  private String orderName;
  private String customerEmail;

  public static BillingConfirmRequestDto of(
      final Subscription subscription, final OrderResponseDto order
  ) {
    User user = subscription.getUser();

    Integer amount = order.getOrderItems()
        .stream()
        .mapToInt(OrderItemResponseDto::getDiscountedPrice)
        .sum();

    return BillingConfirmRequestDto.builder()
        .amount(amount)
        .customerKey(subscription.getBillingKey().getCustomerKey())
        .orderId(order.getOrderId())
        .orderName(order.getName())
        .customerEmail(user.getEmail())
        .build();
  }

}
