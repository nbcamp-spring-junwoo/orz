package com.junwoo.ott.domain.payment.dto.response;

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
public class OrderResponseDto {

  private String orderId;
  private List<OrderItemResponseDto> orderItems;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}

