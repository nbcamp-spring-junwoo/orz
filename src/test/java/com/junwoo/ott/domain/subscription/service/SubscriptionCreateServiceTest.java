package com.junwoo.ott.domain.subscription.service;

import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.junwoo.ott.domain.payment.api.TosspaymentsClient;
import com.junwoo.ott.domain.payment.dto.remote.BillingDto;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.domain.subscription.repository.SubscriptionRepository;
import com.junwoo.ott.domain.subscription.test.SubscriptionServiceTestValues;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
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
class SubscriptionCreateServiceTest implements SubscriptionServiceTestValues {

  @InjectMocks
  SubscriptionCreateService subscriptionCreateService;

  @Mock
  private SubscriptionRepository subscriptionRepository;
  @Mock
  private TosspaymentsClient tosspaymentsClient;
  @Mock
  private EntityManager entityManager;

  FixtureMonkey fx = FixtureMonkey.builder()
      .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
      .build();

  @Nested
  class createSubscription {

    @DisplayName("성공")
    @Test
    void success() {
      // given
      BillingDto TEST_BILLING_DTO = fx.giveMeBuilder(BillingDto.class)
          .set("authenticatedAt", "2021-08-01T00:00:00+09:00")
          .sample();
      Subscription savedSubscription = fx.giveMeOne(Subscription.class);

      given(tosspaymentsClient.getBillingKey(any())).willReturn(TEST_BILLING_DTO);
      given(subscriptionRepository.saveAndFlush(any())).willReturn(savedSubscription);
      given(subscriptionRepository.findById(any())).willReturn(Optional.of(savedSubscription));

      // when
      subscriptionCreateService.createSubscription(TEST_USER_ID, TEST_MEMBERSHIP_ID, TEST_CARD_RESPONSE_DTO);

      // then
      then(subscriptionRepository).should().saveAndFlush(any());
      then(subscriptionRepository).should().findById(any());
    }

  }

}
