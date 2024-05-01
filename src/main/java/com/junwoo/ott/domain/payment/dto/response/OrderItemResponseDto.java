package com.junwoo.ott.domain.payment.dto.response;

import com.junwoo.ott.domain.payment.entity.OrderItem;
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

  public static OrderItemResponseDto of(final OrderItem orderItem) {
    return OrderItemResponseDto.builder()
        .orderItemId(orderItem.getOrderItemId())
        .name(orderItem.getName())
        .itemCode(orderItem.getItemCode())
        .totalPrice(orderItem.getBasePrice())
        .discountingPrice(orderItem.getDiscountingPrice())
        .discountedPrice(orderItem.getDiscountedPrice())
        .build();
  }

}
