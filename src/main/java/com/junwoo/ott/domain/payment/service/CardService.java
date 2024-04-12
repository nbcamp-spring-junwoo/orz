package com.junwoo.ott.domain.payment.service;

import com.junwoo.ott.domain.payment.api.TosspaymentsClient;
import com.junwoo.ott.domain.payment.dto.remote.BillingKeyDto;
import com.junwoo.ott.domain.payment.dto.remote.BillingKeyRequestByAuthKeyDto;
import com.junwoo.ott.domain.payment.dto.request.BillingKeyRequestDto;
import com.junwoo.ott.domain.payment.dto.request.CardsReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.payment.entity.BillingKey;
import com.junwoo.ott.domain.payment.entity.Card;
import com.junwoo.ott.domain.payment.repository.CardRepository;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.exception.custom.CustomCardException;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CardService {

  private final CardRepository cardRepository;
  private final BillingKeyRepository billingKeyRepository;
  private final TosspaymentsClient client;

  public void createBillingKey(final BillingKeyRequestDto requestDto) {
    BillingKeyRequestByAuthKeyDto remoteRequestDto = BillingKeyRequestByAuthKeyDto.of(requestDto);
    BillingKeyDto response = client.getBillingKey(remoteRequestDto);

    if (!Objects.equals(requestDto.customerKey(), response.getCustomerKey())) {
      throw new CustomCardException("Invalid user");
    }

    User parentUser = User.builder().userId(requestDto.userId()).build();

    Card parentCard = response.getCard().toEntity();
    validateSameCardNotExists(parentCard.getNumber());
    parentCard.setParents(parentUser);
    parentCard = cardRepository.save(parentCard);

    BillingKey billingKeyEntity = response.toEntity();
    billingKeyEntity.setParents(parentUser, parentCard);
    billingKeyRepository.save(billingKeyEntity);
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
    if (cardRepository.existsByNumber(cardNumber)) {
      throw new CustomCardException("Card already exists");
    }
  }

}
