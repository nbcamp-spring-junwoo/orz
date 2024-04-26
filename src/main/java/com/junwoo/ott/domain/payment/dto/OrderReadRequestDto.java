package com.junwoo.ott.domain.payment.dto;

import com.junwoo.ott.global.jwt.UserDetailsImpl;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OrderReadRequestDto {

  private String orderId;
  private Long userId;

  public static OrderReadRequestDto of(final String orderId, final UserDetailsImpl userDetails) {
    return new OrderReadRequestDto(orderId, userDetails.getUserId());
  }

}
