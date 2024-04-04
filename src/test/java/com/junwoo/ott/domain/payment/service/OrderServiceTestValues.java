package com.junwoo.ott.domain.payment.service;

import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.domain.payment.entity.OrderItem;

public interface OrderServiceTestValues {

  Membership TEST_MEMBERSHIP = Membership.builder()
      .membershipId(1L)
      .price(10000)
      .build();

  OrderItem TEST_ORDER_ITEM = OrderItem.builder()
      .itemCode("SUBSCRIPTION" + TEST_MEMBERSHIP.getMembershipId())
      .basePrice(TEST_MEMBERSHIP.getPrice())
      .discountingPrice(0)
      .build();

}
