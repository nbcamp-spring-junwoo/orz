package com.junwoo.ott.domain.payment.controller;

import com.junwoo.ott.domain.payment.dto.body.BillingKeyCreateDto;
import com.junwoo.ott.domain.payment.dto.request.BillingKeyRequestDto;
import com.junwoo.ott.domain.payment.dto.request.CardsReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.payment.service.CardService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CardController {

  private final CardService cardService;

  @PostMapping("/api/v1/users/me/billing/key")
  public void postBillingKey(
      final @Validated @RequestBody BillingKeyCreateDto dto,
      final @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    BillingKeyRequestDto requestDto = BillingKeyRequestDto.of(dto, userDetails.getUserId());

    cardService.createBillingKey(requestDto);
  }

  @GetMapping("/api/v1/users/me/cards")
  public ResponseDto<List<CardResponseDto>> getCards(
      final @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    CardsReadRequestDto requestDto = CardsReadRequestDto.of(userDetails.getUserId());
    List<CardResponseDto> result = cardService.getCards(requestDto);

    return ResponseDto.ok(result);
  }

}
