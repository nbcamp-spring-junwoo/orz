package com.junwoo.ott.domain.subscription.dto.request;

import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.exception.custom.UserNotSameException;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SubscriptionRequestDto {

  private Long userId;
  private Long cardId;
  private MembershipType membership;
  private UserDetailsImpl userDetails;

  public static SubscriptionRequestDto of(
      final Long userId,
      final Long cardId,
      final MembershipType membershipType,
      final UserDetailsImpl userDetails
  ) {
    if (!Objects.equals(userId, userDetails.getUserId())) {
      throw new UserNotSameException("Invalid user id");
    }

    return SubscriptionRequestDto.builder()
        .userId(userId)
        .cardId(cardId)
        .membership(membershipType)
        .userDetails(userDetails)
        .build();
  }

}
