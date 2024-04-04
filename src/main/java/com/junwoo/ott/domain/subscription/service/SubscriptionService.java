package com.junwoo.ott.domain.subscription.service;

import com.junwoo.ott.domain.membership.service.MembershipService;
import com.junwoo.ott.domain.payment.api.TosspaymentsClient;
import com.junwoo.ott.domain.payment.dto.remote.PaymentDto;
import com.junwoo.ott.domain.payment.dto.request.BillingConfirmRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.payment.dto.response.OrderResponseDto;
import com.junwoo.ott.domain.payment.service.CardService;
import com.junwoo.ott.domain.payment.service.OrderService;
import com.junwoo.ott.domain.subscription.dto.request.SubscriptionRequestDto;
import com.junwoo.ott.domain.subscription.dto.response.SubscriptionHistoryResponseDto;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.domain.subscription.entity.SubscriptionHistory;
import com.junwoo.ott.domain.subscription.repository.SubscriptionHistoryRepository;
import com.junwoo.ott.domain.subscription.repository.SubscriptionRepository;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.user.service.UserService;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.exception.custom.SubscriptionException;
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
  private final SubscriptionCreateService subscriptionCreateService;

  private final SubscriptionRepository subscriptionRepository;
  private final SubscriptionHistoryRepository subscriptionHistoryRepository;
  private final TosspaymentsClient tosspaymentsClient;

  public SubscriptionHistoryResponseDto requestSubscription(final SubscriptionRequestDto dto) {
    validateUserHasNoSubscription(dto);

    Long userId = dto.getUserId();
    Long cardId = dto.getCardId();
    CardResponseDto validCard = validateUserHasCard(userId, cardId);

    Subscription subscription = getOrCreateSubscription(userId, dto.getMembershipId(), validCard);

    String billingKey = subscription.getBillingKey();
    OrderResponseDto order = orderService.createSubscriptionOrder(subscription, dto.getCouponIssuanceId());

    BillingConfirmRequestDto requestDto = BillingConfirmRequestDto.of(subscription, order.getOrderId());
    PaymentDto confirmResult = tosspaymentsClient.confirmBilling(billingKey, requestDto);

    SubscriptionHistory subscriptionHistory = confirmResult.toSubscriptionHistory();
    subscriptionHistory.setParents(subscription, User.builder().userId(userId).build());

    userService.updateUserMembership(dto.getUserId(), subscription.getMembership()
        .getMembershipType());

    return subscriptionHistoryRepository.save(subscriptionHistory).toResponseDto();
  }

  private Subscription getOrCreateSubscription(
      final Long userId, final Long membershipId, final CardResponseDto card
  ) {
    Optional<Subscription> existingSubscription =
        subscriptionRepository.findByUser_UserIdAndCard_CardIdAndMembership_MembershipId(userId, card.getCardId(), membershipId);

    return existingSubscription.orElseGet(() -> subscriptionCreateService.createSubscription(userId, membershipId, card));
  }

  private void validateUserHasNoSubscription(
      final SubscriptionRequestDto dto
  ) {
    MembershipType fromMembershipType = dto.getUserDetails().getMembershipType();
    MembershipType toMembershipType = membershipService.getMembership(dto.getMembershipId())
        .toEntity()
        .getMembershipType();

    if (fromMembershipType == toMembershipType) {
      throw new SubscriptionException("User already has same subscription: " + fromMembershipType);
    }
  }

  private CardResponseDto validateUserHasCard(final Long userId, final Long cardId) {
    CardReadRequestDto requestDto = CardReadRequestDto.of(userId, cardId, userId);

    return cardService.getCard(requestDto);
  }

}
