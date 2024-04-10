package com.junwoo.ott.domain.subscription.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.junwoo.ott.domain.membership.service.MembershipService;
import com.junwoo.ott.domain.payment.api.TosspaymentsClient;
import com.junwoo.ott.domain.payment.service.CardService;
import com.junwoo.ott.domain.payment.service.OrderService;
import com.junwoo.ott.domain.subscription.dto.request.SubscriptionRequestDto;
import com.junwoo.ott.domain.subscription.repository.SubscriptionHistoryRepository;
import com.junwoo.ott.domain.subscription.repository.SubscriptionRepository;
import com.junwoo.ott.domain.subscription.test.SubscriptionServiceTestValues;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.exception.custom.SubscriptionException;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest implements SubscriptionServiceTestValues {

  @InjectMocks
  private SubscriptionService subscriptionService;
  @Mock
  private CardService cardService;
  @Mock
  private OrderService orderService;
  @Mock
  private UserService userService;
  @Mock
  private MembershipService membershipService;
  @Mock
  private SubscriptionCreateService subscriptionCreateService;
  @Mock
  private SubscriptionRepository subscriptionRepository;
  @Mock
  private SubscriptionHistoryRepository subscriptionHistoryRepository;
  @Mock
  private TosspaymentsClient tosspaymentsClient;

  @DisplayName("requestSubscription")
  @Nested
  class requestSubscription {

    @DisplayName("성공: 이전 구독 정보가 존재하지 않는 경우")
    @Test
    void success_new_subscription() {
      // given
      given(membershipService.getMembershipByMembershipType(any())).willReturn(TEST_MEMBER_SHIP_RESPONSE_DTO);
      given(cardService.getCard(any())).willReturn(TEST_CARD_RESPONSE_DTO);
      given(orderService.createSubscriptionOrder(any(), any())).willReturn(TEST_ORDER_RESPONSE_DTO);
      given(subscriptionCreateService.createSubscription(any(), any(), any())).willReturn(TEST_SUBSCRIPTION);
      given(tosspaymentsClient.confirmBilling(any(), any())).willReturn(TEST_CONFIRM_RESULT);
      given(subscriptionHistoryRepository.save(any())).willReturn(TEST_CONFIRM_RESULT.toSubscriptionHistory());

      // when
      subscriptionService.requestSubscription(TEST_SUBSCRIPTION_REQUEST_DTO);

      // then
      then(subscriptionHistoryRepository).should().save(any());
    }

    @DisplayName("성공: 구독 갱신을 하는 경우")
    @Test
    void success_already_existing_subscription() {
      // given
      given(membershipService.getMembershipByMembershipType(any())).willReturn(TEST_MEMBER_SHIP_RESPONSE_DTO);
      given(cardService.getCard(any())).willReturn(TEST_CARD_RESPONSE_DTO);
      given(orderService.createSubscriptionOrder(any(), any())).willReturn(TEST_ORDER_RESPONSE_DTO);
      given(subscriptionRepository.findByUser_UserIdAndCard_CardIdAndMembership_MembershipType(any(), any(), any()))
          .willReturn(Optional.of(TEST_SUBSCRIPTION));
      given(tosspaymentsClient.confirmBilling(any(), any())).willReturn(TEST_CONFIRM_RESULT);
      given(subscriptionHistoryRepository.save(any())).willReturn(TEST_CONFIRM_RESULT.toSubscriptionHistory());

      // when
      subscriptionService.requestSubscription(TEST_SUBSCRIPTION_REQUEST_DTO);

      // then
      then(subscriptionHistoryRepository).should().save(any());
    }

    @DisplayName("실패: 요청과 동일한 정보로 이미 구독이 존재하는 경우")
    @Test
    void fail_when_subscription_already_exists() {
      // given
      SubscriptionRequestDto testSubscriptionRequestDto = SubscriptionRequestDto.builder()
          .userDetails(new UserDetailsImpl(User.builder()
              .membershipType(MembershipType.ROLE_GOLD)
              .build()))
          .membershipType(MembershipType.ROLE_GOLD)
          .build();

      // when & then
      assertThrows(
          SubscriptionException.class,
          () -> subscriptionService.requestSubscription(testSubscriptionRequestDto),
          "User already has same subscription: " + TEST_USER_MEMBERSHIP
      );
    }

    @DisplayName("실패: 원격 서버 오류(결재 실패 등...)")
    @Test
    void fail_remote_server_error() {
      given(membershipService.getMembershipByMembershipType(any())).willReturn(TEST_MEMBER_SHIP_RESPONSE_DTO);
      given(cardService.getCard(any())).willReturn(TEST_CARD_RESPONSE_DTO);
      given(orderService.createSubscriptionOrder(any(), any())).willReturn(TEST_ORDER_RESPONSE_DTO);
      given(subscriptionRepository.findByUser_UserIdAndCard_CardIdAndMembership_MembershipType(any(), any(), any()))
          .willReturn(Optional.of(TEST_SUBSCRIPTION));
      given(tosspaymentsClient.confirmBilling(any(), any())).willThrow(SubscriptionException.class);

      // when & then
      assertThrows(
          SubscriptionException.class,
          () -> subscriptionService.requestSubscription(TEST_SUBSCRIPTION_REQUEST_DTO)
      );
    }

  }

}
