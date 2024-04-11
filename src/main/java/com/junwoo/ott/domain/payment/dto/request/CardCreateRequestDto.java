package com.junwoo.ott.domain.payment.dto.request;

import com.junwoo.ott.domain.payment.dto.body.CardCreateDto;
import com.junwoo.ott.domain.payment.entity.Card;
import java.time.LocalDate;
import lombok.Builder;

@Builder
public record CardCreateRequestDto(
    String cardNumber,
    String cardExpirationYear,
    String cardExpirationMonth,
    LocalDate customerIdentityNumber,
    String cardPassword,
    String customerName,
    Long userId
) {

  public static CardCreateRequestDto of(
      final CardCreateDto dto,
      final Long userId
  ) {
    return CardCreateRequestDto.builder()
        .cardNumber(dto.cardNumber())
        .cardExpirationYear(dto.cardExpirationYear())
        .cardExpirationMonth(dto.cardExpirationMonth())
        .customerIdentityNumber(dto.customerIdentityNumber())
        .cardPassword(dto.cardPassword())
        .customerName(dto.customerName())
        .userId(userId)
        .build();
  }

  public Card toEntity() {
    return Card.builder()
        .cardNumber(cardNumber())
        .cardExpirationYear(cardExpirationYear())
        .cardExpirationMonth(cardExpirationMonth())
        .cardPassword(cardPassword())
        .customerName(customerName())
        .build();
  }

}
