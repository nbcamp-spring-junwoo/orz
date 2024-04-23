package com.junwoo.ott.domain.payment.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemResponseDto {

  private Long orderItemId;
  private String name;
  private String itemCode;
  private Integer totalPrice;
  private Integer discountingPrice;
  private Integer discountedPrice;

}
