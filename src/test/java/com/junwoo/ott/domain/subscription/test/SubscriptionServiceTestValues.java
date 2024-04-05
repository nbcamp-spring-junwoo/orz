package com.junwoo.ott.domain.subscription.test;

import com.junwoo.ott.domain.membership.dto.response.MemberShipResponseDto;
import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.domain.payment.dto.remote.PaymentDto;
import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.payment.dto.response.OrderItemResponseDto;
import com.junwoo.ott.domain.payment.dto.response.OrderResponseDto;
import com.junwoo.ott.domain.payment.entity.Card;
import com.junwoo.ott.domain.payment.entity.OrderItem;
import com.junwoo.ott.domain.subscription.dto.request.SubscriptionRequestDto;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import java.time.LocalDate;
import java.time.LocalDateTime;

public interface SubscriptionServiceTestValues {

  Long TEST_USER_ID = 1L;
  LocalDate TEST_USER_BORN = LocalDate.of(1995, 1, 1);
  MembershipType TEST_USER_MEMBERSHIP = MembershipType.ROLE_NORMAL;
  Long TEST_CARD_ID = 1L;
  Long TEST_MEMBERSHIP_ID = 2L;
  String TEST_CARD_NUMBER = "1234567890123456";

  Membership TEST_MEMBERSHIP = Membership.builder()
      .membershipId(TEST_MEMBERSHIP_ID)
      .membershipType(MembershipType.ROLE_BRONZE)
      .price(10000)
      .build();
  User TEST_USER = User.builder()
      .userId(TEST_USER_ID)
      .born(TEST_USER_BORN)
      .membershipType(TEST_USER_MEMBERSHIP)
      .build();
  Card TEST_CARD = Card.builder()
      .cardId(TEST_CARD_ID)
      .cardNumber(TEST_CARD_NUMBER)
      .cardExpirationYear("22")
      .cardExpirationMonth("12")
      .cardPassword("123")
      .customerName("TEST_USER")
      .user(TEST_USER)
      .build();
  Subscription TEST_SUBSCRIPTION = Subscription.builder()
      .subscriptionId(1L)
      .customerKey("customerKey")
      .billingKey("billingKey")
      .authenticatedAt(LocalDateTime.now())
      .membership(TEST_MEMBERSHIP)
      .user(TEST_USER)
      .card(TEST_CARD)
      .build();
  OrderItem TEST_ORDER_ITEM = OrderItem.builder()
      .itemCode("12")
      .basePrice(10000)
      .discountingPrice(0)
      .build();
  UserDetailsImpl TEST_USER_DETAILS = new UserDetailsImpl(TEST_USER);

  SubscriptionRequestDto TEST_SUBSCRIPTION_REQUEST_DTO = SubscriptionRequestDto.builder()
      .userId(TEST_USER_ID)
      .cardId(TEST_CARD_ID)
      .membershipId(2L)
      .userDetails(TEST_USER_DETAILS)
      .build();
  MemberShipResponseDto TEST_MEMBER_SHIP_RESPONSE_DTO = TEST_MEMBERSHIP.toResponseDto();
  CardResponseDto TEST_CARD_RESPONSE_DTO = TEST_CARD.toResponseDto();
  OrderItemResponseDto TEST_ORDER_ITEM_RESPONSE_DTO = TEST_ORDER_ITEM.toResponseDto();
  OrderResponseDto TEST_ORDER_RESPONSE_DTO = OrderResponseDto.builder()
      .orderId("orderId")
      .createdAt(LocalDateTime.now())
      .updatedAt(LocalDateTime.now())
      .build();
  PaymentDto TEST_CONFIRM_RESULT = PaymentDto.builder().orderId("orderId").build();

}
