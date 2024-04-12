package com.junwoo.ott.domain.payment.dto.request;

import com.junwoo.ott.domain.payment.dto.body.BillingKeyCreateDto;

public record BillingKeyRequestDto(
    String customerKey,
    String authKey,
    Long userId
) {

  public static BillingKeyRequestDto of(final BillingKeyCreateDto dto, final Long userId) {
    return new BillingKeyRequestDto(dto.customerKey(), dto.authKey(), userId);
  }

}
