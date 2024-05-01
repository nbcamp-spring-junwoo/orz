package com.junwoo.ott.domain.payment.dto.remote.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 카드사의 즉시 할인 프로모션을 적용한 금액입니다. 즉시 할인 프로모션이 적용됐을 때만 생성됩니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class DiscountDto {

  // 카드사의 즉시 할인 프로모션을 적용한 금액입니다.
  Integer amount;

}
