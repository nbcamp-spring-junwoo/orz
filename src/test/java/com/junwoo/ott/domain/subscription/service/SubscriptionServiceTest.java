package com.junwoo.ott.domain.subscription.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.junwoo.ott.domain.membership.dto.response.MemberShipResponseDto;
import com.junwoo.ott.domain.membership.service.MembershipService;
import com.junwoo.ott.domain.payment.api.TosspaymentsClient;
import com.junwoo.ott.domain.payment.dto.remote.PaymentDto;
import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.payment.dto.response.OrderResponseDto;
import com.junwoo.ott.domain.payment.entity.BillingKey;
import com.junwoo.ott.domain.payment.service.BillingKeyRepository;
import com.junwoo.ott.domain.payment.service.CardService;
import com.junwoo.ott.domain.payment.service.OrderService;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.domain.subscription.entity.SubscriptionHistory;
import com.junwoo.ott.domain.subscription.repository.SubscriptionHistoryRepository;
import com.junwoo.ott.domain.subscription.repository.SubscriptionRepository;
import com.junwoo.ott.domain.subscription.test.SubscriptionServiceTestValues;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.exception.custom.SubscriptionException;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.FieldReflectionArbitraryIntrospector;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class SubscriptionServiceTest implements
    SubscriptionServiceTestValues {

  @Mock
  private CardService cardService;
  @Mock
  private UserService userService;
  @Mock
  private OrderService orderService;
  @Mock
  private MembershipService membershipService;
  @Mock
  private BillingKeyRepository billingKeyRepository;
  @Mock
  private SubscriptionRepository subscriptionRepository;
  @Mock
  private SubscriptionHistoryRepository subscriptionHistoryRepository;
  @Mock
  private TosspaymentsClient client;
  @Mock
  private EntityManager entityManager;
  @InjectMocks
  private SubscriptionService subscriptionService;

  private final FixtureMonkey fm = FixtureMonkey.builder()
      .objectIntrospector(FieldReflectionArbitraryIntrospector.INSTANCE)
      .build();

  @Nested
  @DisplayName("subscribe")
  class Subscribe {

    @Test
    @DisplayName("should subscribe successfully when user has no active subscription")
    void shouldSubscribeSuccessfullyWhenUserHasNoActiveSubscription() {
      // given
      CardResponseDto cardResponseDto = fm.giveMeOne(CardResponseDto.class);
      Subscription subscription = fm.giveMeOne(Subscription.class);
      OrderResponseDto orderResponseDto = fm.giveMeOne(OrderResponseDto.class);
      PaymentDto paymentDto = fm.giveMeOne(PaymentDto.class);
      MemberShipResponseDto memberShipResponseDto = fm.giveMeOne(MemberShipResponseDto.class);

      given(cardService.getCard(any())).willReturn(cardResponseDto);
      given(subscriptionRepository.findByUser_UserIdAndCard_CardIdAndMembership_MembershipId(any(), any(), any())).willReturn(Optional.empty());
      given(subscriptionRepository.saveAndFlush(any())).willReturn(subscription);
      given(subscriptionRepository.findById(any())).willReturn(Optional.of(subscription));
      given(orderService.createSubscriptionOrder(any(), any())).willReturn(orderResponseDto);
      given(client.confirmBilling(any(), any())).willReturn(paymentDto);
      given(membershipService.getMembershipByMembershipType(any())).willReturn(memberShipResponseDto);
      given(billingKeyRepository.findByUser_UserIdAndCard_CardId(any(), any())).willReturn(Optional.of(fm.giveMeOne(BillingKey.class)));
      given(subscriptionHistoryRepository.save(any())).willReturn(fm.giveMeOne(SubscriptionHistory.class));

      // when
      subscriptionService.subscribe(TEST_SUBSCRIPTION_REQUEST_DTO);

      // then
      then(userService).should().updateUserMembership(any(), any());
      then(subscriptionHistoryRepository).should().save(any());
    }

    @Test
    @DisplayName("should throw exception when user has active subscription")
    void shouldThrowExceptionWhenUserHasActiveSubscription() {
      // given

      given(subscriptionHistoryRepository.existsActiveSubscription(any())).willReturn(true);

      // when & then
      assertThrows(SubscriptionException.class, () -> subscriptionService.subscribe(TEST_SUBSCRIPTION_REQUEST_DTO));
    }

    @Test
    @DisplayName("should throw exception when user has same subscription")
    void shouldThrowExceptionWhenUserHasSameSubscription() {
      // given
      MemberShipResponseDto memberShipResponseDto = fm.giveMeOne(MemberShipResponseDto.class);

      given(cardService.getCard(any())).willReturn(fm.giveMeOne(CardResponseDto.class));
      given(membershipService.getMembershipByMembershipType(any())).willReturn(memberShipResponseDto);

      // when & then
      assertThrows(SubscriptionException.class, () -> subscriptionService.subscribe(TEST_SUBSCRIPTION_REQUEST_DTO));
    }

  }

}
