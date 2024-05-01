package com.junwoo.ott.domain.payment.dto.body;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentConfirmDto {

  @NotNull
  private String paymentKey;
  @NotNull
  private String orderId;
  @NotNull
  private Integer amount;

}
