package com.junwoo.ott.domain.payment.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.junwoo.ott.domain.payment.dto.request.CardCreateRequestDto;
import com.junwoo.ott.domain.payment.dto.request.CardsReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.payment.entity.Card;
import com.junwoo.ott.domain.payment.repository.CardRepository;
import com.junwoo.ott.domain.user.UserTestValues;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class CardServiceTest implements UserTestValues {

  @Mock
  private CardRepository cardRepository;
  @InjectMocks
  private CardService cardService;
  private final FixtureMonkey fm = FixtureMonkey.builder()
      .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
      .build();

  @DisplayName("createCard")
  @Nested
  class createCard {

    @DisplayName("성공")
    @Test
    void success() {
      // given
      CardCreateRequestDto requestDto = fm.giveMeOne(CardCreateRequestDto.class);

      // when
      cardService.createCard(requestDto);

      // then
      then(cardRepository).should().save(any());
    }

  }

  @DisplayName("getCard")
  @Nested
  class getCard {

    @DisplayName("성공")
    @Test
    void success() {
      // given
      CardReadRequestDto requestDto = fm.giveMeOne(CardReadRequestDto.class);
      Card card = fm.giveMeBuilder(Card.class).set("user", TEST_USER).sample();

      given(cardRepository.findById(any())).willReturn(Optional.of(card));

      // when
      CardResponseDto result = cardService.getCard(requestDto);

      // then
      assertNotNull(result);
      then(cardRepository).should().findById(any());
    }

  }


  @DisplayName("getCards")
  @Nested
  class getCards {

    @DisplayName("성공")
    @Test
    void success() {
      // given
      CardsReadRequestDto requestDto = fm.giveMeBuilder(CardsReadRequestDto.class).sample();
      Card card = fm.giveMeBuilder(Card.class).set("user", TEST_USER).sample();

      given(cardRepository.findAllByUser_UserId(any())).willReturn(List.of(card));

      List<CardResponseDto> result = cardService.getCards(requestDto);

      // then
      assertFalse(result.isEmpty());
    }

  }

}
