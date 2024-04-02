package com.junwoo.ott.domain.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class CardResponseDto {

  private Long cardId;
  private String cardNumber;
  private String cardExpirationYear;
  private String cardExpirationMonth;
  private String customerIdentityNumber;
  private String customerName;

}
