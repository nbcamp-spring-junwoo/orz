package com.junwoo.ott.domain.payment.dto;

import com.junwoo.ott.domain.payment.dto.response.OrderItemResponseDto;
import com.junwoo.ott.domain.payment.entity.Order;
import com.junwoo.ott.domain.payment.entity.OrderItem;
import java.time.LocalDateTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderReadResponseDto {

  private String orderId;
  private String orderName;
  private LocalDateTime createdAt;
  private List<OrderItemResponseDto> orderItems;

  public static OrderReadResponseDto of(final Order order) {
    List<OrderItem> orderItems = order.getOrderItems();

    return OrderReadResponseDto.builder()
        .orderId(order.getOrderId())
        .orderName(order.getName())
        .createdAt(order.getCreatedAt())
        .orderItems(orderItems.stream().map(OrderItemResponseDto::of).toList())
        .build();
  }

}
