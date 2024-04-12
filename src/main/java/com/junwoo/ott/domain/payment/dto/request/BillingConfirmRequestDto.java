package com.junwoo.ott.domain.payment.dto.request;

import com.junwoo.ott.domain.membership.entity.Membership;
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
  private Integer taxFreeAmount;
  private Integer taxExemptionAmount;

  public static BillingConfirmRequestDto of(final Subscription subscription, final String orderId) {
    User user = subscription.getUser();
    Membership membership = subscription.getMembership();
    // TODO: 매직넘버 없애기
    String orderName = membership.getMembershipType().name().substring(5) + " 구독";

    return BillingConfirmRequestDto.builder()
        .amount(membership.getPrice())
        .customerKey(subscription.getBillingKey().getCustomerKey())
        .orderId(orderId)
        .orderName(orderName)
        .customerEmail(user.getEmail())
        .taxFreeAmount(0)
        .taxExemptionAmount(membership.getPrice())
        .build();
  }

}
