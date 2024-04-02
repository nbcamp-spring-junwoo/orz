package com.junwoo.ott.domain.payment.dto.request;

import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.payment.util.CustomerKeyGenerator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BillingKeyRequestDto {

  // 구매자 ID입니다. 빌링키와 연결됩니다.
  private String customerKey;
  // 카드 번호입니다.
  private String cardNumber;
  // 카드 유효 년도입니다.
  private String cardExpirationYear;
  // 카드 유효 월입니다.
  private String cardExpirationMonth;
  // 카드 소유자 생년월일입니다.
  private String customerIdentityNumber;

  public static BillingKeyRequestDto of(final CardResponseDto card) {
    return BillingKeyRequestDto.builder()
        .customerKey(CustomerKeyGenerator.generateCustomerKey())
        .cardNumber(card.getCardNumber())
        .cardExpirationYear(card.getCardExpirationYear())
        .cardExpirationMonth(card.getCardExpirationMonth())
        .customerIdentityNumber(card.getCustomerIdentityNumber())
        .build();
  }

}
