package com.junwoo.ott.domain.payment.dto.remote;

import com.junwoo.ott.domain.payment.dto.request.BillingKeyRequestDto;

public record BillingKeyRequestByAuthKeyDto(
    String customerKey,
    String authKey
) {

  public static BillingKeyRequestByAuthKeyDto of(final BillingKeyRequestDto requestDto) {
    return new BillingKeyRequestByAuthKeyDto(requestDto.customerKey(), requestDto.authKey());
  }

}
