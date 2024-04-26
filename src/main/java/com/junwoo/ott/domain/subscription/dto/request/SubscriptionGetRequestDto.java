package com.junwoo.ott.domain.subscription.dto.request;

import com.junwoo.ott.global.jwt.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SubscriptionGetRequestDto {

  private Long userId;

  public static SubscriptionGetRequestDto of(final UserDetailsImpl userDetails) {
    return new SubscriptionGetRequestDto(userDetails.getUserId());
  }

}
