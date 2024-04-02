package com.junwoo.ott.domain.payment.service;

import com.junwoo.ott.domain.payment.dto.request.CardCreateRequestDto;
import com.junwoo.ott.domain.payment.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CardService {

  private final CardRepository cardRepository;

  public void createCard(CardCreateRequestDto requestDto) {
    validateSameCardNotExists(requestDto.cardNumber());

    cardRepository.save(requestDto.toEntity());
  }

  private void validateSameCardNotExists(String cardNumber) {
    if (cardRepository.existsByCardNumber(cardNumber)) {
      throw new IllegalArgumentException("Card already exists");
    }
  }

}
