package com.junwoo.ott.domain.payment.dto.request;

import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.domain.payment.entity.Card;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.domain.user.entity.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BillingConfirmRequestDto {

  private String customerKey;
  private Integer amount;
  private String orderName;
  private String orderId;
  private String customerEmail;
  private String customerName;
  private Integer taxFreeAmount;
  private Integer taxExemptionAmount;

  public static BillingConfirmRequestDto of(final Subscription subscription, final String orderId) {
    User user = subscription.getUser();
    Card card = subscription.getCard();
    Membership membership = subscription.getMembership();

    return BillingConfirmRequestDto.builder()
        .customerKey(subscription.getCustomerKey())
        .amount(membership.getPrice())
        .orderId(orderId)
        .orderName(membership.getMembershipType().name())
        .customerEmail(user.getEmail())
        .customerName(card.getCustomerName())
        .taxFreeAmount(0)
        .taxExemptionAmount(membership.getPrice())
        .build();
  }

}
