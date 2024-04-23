package com.junwoo.ott.domain.subscription.service;

import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.domain.membership.service.MembershipService;
import com.junwoo.ott.domain.payment.api.TosspaymentsClient;
import com.junwoo.ott.domain.payment.dto.remote.PaymentDto;
import com.junwoo.ott.domain.payment.dto.request.BillingConfirmRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.payment.dto.response.OrderResponseDto;
import com.junwoo.ott.domain.payment.entity.BillingKey;
import com.junwoo.ott.domain.payment.entity.Card;
import com.junwoo.ott.domain.payment.service.BillingKeyRepository;
import com.junwoo.ott.domain.payment.service.CardService;
import com.junwoo.ott.domain.payment.service.OrderService;
import com.junwoo.ott.domain.subscription.dto.request.SubscriptionRequestDto;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.domain.subscription.entity.SubscriptionHistory;
import com.junwoo.ott.domain.subscription.repository.SubscriptionHistoryRepository;
import com.junwoo.ott.domain.subscription.repository.SubscriptionRepository;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.exception.custom.SubscriptionException;
import jakarta.persistence.EntityManager;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class SubscriptionService {

  private final CardService cardService;
  private final UserService userService;
  private final OrderService orderService;
  private final MembershipService membershipService;

  private final BillingKeyRepository billingKeyRepository;
  private final SubscriptionRepository subscriptionRepository;
  private final SubscriptionHistoryRepository subscriptionHistoryRepository;
  private final TosspaymentsClient paymentClient;

  private final EntityManager entityManager;

  public void subscribe(final SubscriptionRequestDto subscriptionRequest) {
    validateUserHasNoActiveSubscription(subscriptionRequest);

    Long userId = subscriptionRequest.getUserId();
    Long cardId = subscriptionRequest.getCardId();
    CardResponseDto validCard = validateUserHasCard(userId, cardId);

    Long membershipId = getMembershipId(subscriptionRequest.getMembershipType());
    Subscription subscription = getOrCreateSubscription(userId, validCard.getCardId(), membershipId);

    Long couponIssuanceId = subscriptionRequest.getCouponIssuanceId();
    OrderResponseDto order = orderService.createSubscriptionOrder(subscription, couponIssuanceId);

    BillingConfirmRequestDto billingRequest = BillingConfirmRequestDto.of(subscription, order);
    PaymentDto paymentConfirmation = paymentClient.confirmBilling(subscription.getBillingKey()
        .getKey(), billingRequest);

    SubscriptionHistory subscriptionHistory = paymentConfirmation.toSubscriptionHistory();
    subscriptionHistory.setParents(subscription);

    userService.updateUserMembership(subscriptionRequest.getUserId(), subscription.getMembership()
        .getMembershipType());
    subscriptionHistoryRepository.save(subscriptionHistory);
  }

  private Long getMembershipId(final MembershipType membershipType) {
    return membershipService.getMembershipByMembershipType(membershipType)
        .getMembershipId();
  }

  private Subscription getOrCreateSubscription(
      final Long userId, final Long cardId, final Long membershipId
  ) {
    Optional<Subscription> existingSubscription = subscriptionRepository.findByUser_UserIdAndCard_CardIdAndMembership_MembershipId(userId, cardId, membershipId);

    return existingSubscription.orElseGet(() -> createSubscription(userId, cardId, membershipId));
  }

  private Subscription createSubscription(
      final Long userId, final Long cardId, final Long membershipId
  ) {
    Subscription subscription = new Subscription();

    User parentUser = User.builder().userId(userId).build();
    Card parentCard = Card.builder().cardId(cardId).build();
    Membership parentMembership = Membership.builder().membershipId(membershipId).build();
    BillingKey billingKey = billingKeyRepository.findByUser_UserIdAndCard_CardId(userId, cardId)
        .orElseThrow(() -> new SubscriptionException("BillingKey not found"));
    subscription.setParents(parentUser, parentCard, parentMembership, billingKey);

    Subscription savedSubscription = subscriptionRepository.saveAndFlush(subscription);
    entityManager.detach(savedSubscription);

    return subscriptionRepository.findById(savedSubscription.getSubscriptionId())
        .orElseThrow(() -> new SubscriptionException("Subscription not found"));
  }

  private void validateUserHasNoActiveSubscription(
      final SubscriptionRequestDto dto
  ) {
    MembershipType fromMembershipType = dto.getUserDetails().getMembershipType();
    MembershipType toMembershipType = dto.getMembershipType();

    boolean isBadRequest = (fromMembershipType == toMembershipType)
                           || subscriptionHistoryRepository.existsActiveSubscription(dto.getUserId());
    if (isBadRequest) {
      throw new SubscriptionException("User already has same subscription: " + fromMembershipType);
    }
  }

  private CardResponseDto validateUserHasCard(final Long userId, final Long cardId) {
    CardReadRequestDto requestDto = CardReadRequestDto.of(userId, cardId, userId);

    return cardService.getCard(requestDto);
  }

}

// TODO: 신규가입, 갱신 분리
