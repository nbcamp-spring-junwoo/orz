package com.junwoo.ott.domain.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class OrderItemResponseDto {

  private Long orderItemId;
  private String itemCode;
  private Integer totalPrice;
  private Integer discountingPrice;
  private Integer discountedPrice;

}
