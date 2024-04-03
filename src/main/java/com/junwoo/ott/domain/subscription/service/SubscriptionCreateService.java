package com.junwoo.ott.domain.subscription.service;

import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.domain.payment.api.TosspaymentsClient;
import com.junwoo.ott.domain.payment.dto.remote.BillingDto;
import com.junwoo.ott.domain.payment.dto.request.BillingKeyRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.payment.entity.Card;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.domain.subscription.repository.SubscriptionRepository;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.exception.custom.SubscriptionException;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SubscriptionCreateService {

  private final SubscriptionRepository subscriptionRepository;

  private final TosspaymentsClient tosspaymentsClient;
  private final EntityManager entityManager;

  public Subscription createSubscription(
      final Long userId, final Long membershipId, final CardResponseDto card
  ) {
    BillingKeyRequestDto requestDto = BillingKeyRequestDto.of(card);
    BillingDto billingKey = tosspaymentsClient.getBillingKey(requestDto);
    Subscription subscription = billingKey.toEntity();

    User parentUser = User.builder().userId(userId).build();
    Membership parentMembership = Membership.builder().membershipId(membershipId).build();
    Card parentCard = Card.builder().cardId(card.getCardId()).build();
    subscription.setParents(parentUser, parentMembership, parentCard);

    Subscription saved = subscriptionRepository.saveAndFlush(subscription);
    entityManager.detach(saved);

    return subscriptionRepository.findById(saved.getSubscriptionId()).orElseThrow(
        () -> new SubscriptionException("Subscription 생성 실패")
    );
  }

}
