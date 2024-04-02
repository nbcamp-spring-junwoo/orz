package com.junwoo.ott.domain.payment.dto.response;


import java.util.Objects;
import lombok.Builder;

@Builder
public record CardReadRequestDto(
    Long userId,
    Long cardId

) {

  public static CardReadRequestDto of(
      final Long userId,
      final Long cardId,
      final Long userIdDetails
  ) {
    if (!Objects.equals(userId, userIdDetails)) {
      throw new IllegalArgumentException("User id not match");
    }

    return CardReadRequestDto.builder()
        .userId(userId)
        .cardId(cardId)
        .build();
  }

}
