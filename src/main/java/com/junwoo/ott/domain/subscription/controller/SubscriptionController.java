package com.junwoo.ott.domain.subscription.controller;

import com.junwoo.ott.domain.subscription.dto.request.SubscriptionRequestDto;
import com.junwoo.ott.domain.subscription.service.SubscriptionService;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscriptionController {

  private final SubscriptionService subscriptionService;

  @PostMapping("/api/v1/users/{userId}/subscriptions")
  public void requestSubscription(
      @PathVariable @Positive final Long userId,
      @RequestParam("card") final Long cardId,
      @RequestParam("membership") final String membershipType,
      @RequestParam(value = "coupon", required = false) final Long couponIssuanceId,
      @AuthenticationPrincipal final UserDetailsImpl userDetails
  ) {
    SubscriptionRequestDto requestDto = SubscriptionRequestDto.of(
        userId,
        cardId,
        membershipType,
        couponIssuanceId,
        userDetails
    );

    subscriptionService.requestSubscription(requestDto);
  }

}
