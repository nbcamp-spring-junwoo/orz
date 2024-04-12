package com.junwoo.ott.domain.payment.dto.body;

public record BillingKeyCreateDto(
    String customerKey,
    String authKey
) {

}
