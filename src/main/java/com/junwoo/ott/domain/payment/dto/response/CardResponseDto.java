package com.junwoo.ott.domain.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CardResponseDto {

  private Long cardId;
  private String cardNumber;
  private String cardNickname;

}
