package com.junwoo.ott.domain.payment.controller;

import com.junwoo.ott.domain.payment.dto.body.CardCreateDto;
import com.junwoo.ott.domain.payment.dto.request.CardCreateRequestDto;
import com.junwoo.ott.domain.payment.service.CardService;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
public class CardController {

  private final CardService cardService;

  @PostMapping("/api/v1/users/{userId}/cards")
  public void postCard(
      final @Positive @PathVariable Long userId,
      final @Validated CardCreateDto dto,
      final @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    CardCreateRequestDto requestDto = CardCreateRequestDto.of(dto, userId, userDetails.getUserId());

    cardService.createCard(requestDto);
  }

}
