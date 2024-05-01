package com.junwoo.ott.domain.subscription.dto.response;

import com.junwoo.ott.domain.payment.entity.Order;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.domain.subscription.entity.SubscriptionHistory;
import com.junwoo.ott.global.customenum.SubscriptionStatusType;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionGetResponseDto {

  private Long subscriptionHistoryId;
  private String membershipType;
  private SubscriptionStatusType status;
  private LocalDate startAt;
  private LocalDate expireAt;

  private String orderId;

  public static SubscriptionGetResponseDto of(
      final SubscriptionHistory history
  ) {
    Subscription subscription = history.getSubscription();
    Order order = history.getOrder();

    return SubscriptionGetResponseDto.builder()
        .subscriptionHistoryId(history.getSubscriptionHistoryId())
        .membershipType(subscription.getMembership().getMembershipType().name())
        .status(history.getStatus())
        .startAt(history.getCreatedAt().toLocalDate())
        .expireAt(history.getExpireAt())

        .orderId(order.getOrderId())
        .build();
  }

}
