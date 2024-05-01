package com.junwoo.ott.domain.payment.dto.request;

import lombok.Builder;

@Builder
public record CardsReadRequestDto(
    Long userId
) {

  public static CardsReadRequestDto of(
      final Long userId
  ) {
    return CardsReadRequestDto.builder()
        .userId(userId)
        .build();
  }

}
