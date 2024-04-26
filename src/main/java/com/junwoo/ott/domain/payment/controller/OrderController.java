package com.junwoo.ott.domain.payment.controller;

import com.junwoo.ott.domain.payment.dto.OrderReadRequestDto;
import com.junwoo.ott.domain.payment.dto.OrderReadResponseDto;
import com.junwoo.ott.domain.payment.service.OrderService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class OrderController {

  private final OrderService orderService;

  @GetMapping("/api/v1/orders/{orderId}")
  public ResponseDto<OrderReadResponseDto> getOrder(
      final @PathVariable String orderId,
      final @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    OrderReadRequestDto requestDto = OrderReadRequestDto.of(orderId, userDetails);
    OrderReadResponseDto result = orderService.readOrder(requestDto);

    return ResponseDto.ok(result);
  }

}

