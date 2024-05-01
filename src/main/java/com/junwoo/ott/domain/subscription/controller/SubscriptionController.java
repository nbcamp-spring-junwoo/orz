package com.junwoo.ott.domain.subscription.controller;

import com.junwoo.ott.domain.subscription.dto.request.SubscriptionGetRequestDto;
import com.junwoo.ott.domain.subscription.dto.request.SubscriptionRequestDto;
import com.junwoo.ott.domain.subscription.dto.response.SubscriptionGetResponseDto;
import com.junwoo.ott.domain.subscription.service.SubscriptionService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import jakarta.validation.constraints.Positive;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class SubscriptionController {

  private final SubscriptionService subscriptionService;

  @GetMapping("/api/v1/users/me/subscriptions")
  public ResponseDto<List<SubscriptionGetResponseDto>> getSubscriptions(
      @AuthenticationPrincipal final UserDetailsImpl userDetails
  ) {
    SubscriptionGetRequestDto requestDto = SubscriptionGetRequestDto.of(userDetails);
    List<SubscriptionGetResponseDto> subscriptions = subscriptionService.getSubscriptions(requestDto);

    return ResponseDto.ok(subscriptions);
  }

  @PostMapping("/api/v1/users/{userId}/subscriptions")
  public void postSubscription(
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

    subscriptionService.subscribe(requestDto);
  }

}
