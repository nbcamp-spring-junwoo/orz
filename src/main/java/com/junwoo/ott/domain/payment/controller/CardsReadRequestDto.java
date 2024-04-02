package com.junwoo.ott.domain.payment.controller;

import com.junwoo.ott.global.exception.custom.UserNotSameException;
import java.util.Objects;
import lombok.Builder;

@Builder
public record CardsReadRequestDto(
    Long userId
) {

  public static CardsReadRequestDto of(
      final Long userId,
      final Long userIdDetails
  ) {
    if (!Objects.equals(userId, userIdDetails)) {
      throw new UserNotSameException("User id not match");
    }

    return CardsReadRequestDto.builder()
        .userId(userId)
        .build();
  }

}
