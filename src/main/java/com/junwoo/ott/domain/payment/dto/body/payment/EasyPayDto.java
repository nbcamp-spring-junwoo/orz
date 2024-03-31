package com.junwoo.ott.domain.payment.dto.body.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EasyPayDto {

  private Integer amount;
  private String provider;
  private Integer discountAmount;

}
