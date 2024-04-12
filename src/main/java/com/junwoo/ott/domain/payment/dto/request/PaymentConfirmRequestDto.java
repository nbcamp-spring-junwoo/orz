package com.junwoo.ott.domain.payment.dto.request;

import com.junwoo.ott.domain.payment.dto.body.PaymentConfirmDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentConfirmRequestDto {

  private String paymentKey;
  private String orderId;
  private Integer amount;

  private Long userid;

  public PaymentConfirmRequestDto(final PaymentConfirmDto paymentConfirmDto) {
    this.paymentKey = paymentConfirmDto.getPaymentKey();
    this.orderId = paymentConfirmDto.getOrderId();
    this.amount = paymentConfirmDto.getAmount();
  }

}
