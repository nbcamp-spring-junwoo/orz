package com.junwoo.ott.domain.payment.service;

import com.junwoo.ott.domain.payment.dto.request.CardCreateRequestDto;
import com.junwoo.ott.domain.payment.dto.request.CardsReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.payment.entity.Card;
import com.junwoo.ott.domain.payment.repository.CardRepository;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.exception.custom.CustomCardException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CardService {

  private final CardRepository cardRepository;

  public void createCard(final CardCreateRequestDto requestDto) {
    validateSameCardNotExists(requestDto.cardNumber());

    Card entity = requestDto.toEntity();
    entity.setParents(User.builder().userId(requestDto.userId()).build());

    cardRepository.save(entity);
  }

  public CardResponseDto getCard(final CardReadRequestDto requestDto) {
    Card card = cardRepository.findById(requestDto.cardId())
        .orElseThrow(() -> new CustomCardException("Card not found"));

    return card.toResponseDto();
  }

  public List<CardResponseDto> getCards(final CardsReadRequestDto requestDto) {
    List<Card> cards = cardRepository.findAllByUser_UserId(requestDto.userId());

    return cards.stream().map(Card::toResponseDto).toList();
  }

  private void validateSameCardNotExists(final String cardNumber) {
    if (cardRepository.existsByCardNumber(cardNumber)) {
      throw new CustomCardException("Card already exists");
    }
  }

}
