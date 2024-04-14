package com.junwoo.ott.domain.payment.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.never;

import com.junwoo.ott.domain.payment.api.TosspaymentsClient;
import com.junwoo.ott.domain.payment.dto.remote.BillingKeyDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.CardDto;
import com.junwoo.ott.domain.payment.dto.request.BillingKeyRequestDto;
import com.junwoo.ott.domain.payment.dto.request.CardsReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardReadRequestDto;
import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.payment.entity.Card;
import com.junwoo.ott.domain.payment.repository.CardRepository;
import com.junwoo.ott.domain.user.UserTestValues;
import com.junwoo.ott.global.exception.custom.CustomCardException;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.BuilderArbitraryIntrospector;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
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
  @Mock
  private BillingKeyRepository billingKeyRepository;
  @Mock
  private TosspaymentsClient client;
  @InjectMocks
  private CardService cardService;
  private final FixtureMonkey fm = FixtureMonkey.builder()
      .objectIntrospector(BuilderArbitraryIntrospector.INSTANCE)
      .build();
  private final FixtureMonkey fmRecord = FixtureMonkey.builder()
      .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
      .build();

  @DisplayName("createBillingKey")
  @Nested
  class CreateBillingKey {

    @DisplayName("should create billing key successfully")
    @Test
    void shouldCreateBillingKeySuccessfully() {
      // given
      BillingKeyRequestDto requestDto = fmRecord.giveMeOne(BillingKeyRequestDto.class);
      BillingKeyDto response = fm.giveMeBuilder(BillingKeyDto.class)
          .set("customerKey", requestDto.customerKey())
          .sample();

      given(client.getBillingKey(any())).willReturn(response);
      given(cardRepository.existsByNumber(any())).willReturn(false);

      // when
      cardService.createBillingKey(requestDto);

      // then
      then(cardRepository).should().save(any());
      then(billingKeyRepository).should().save(any());
    }

    @DisplayName("should throw exception when card already exists")
    @Test
    void shouldThrowExceptionWhenCardAlreadyExists() {
      // given
      BillingKeyRequestDto requestDto = fmRecord.giveMeOne(BillingKeyRequestDto.class);
      BillingKeyDto response = fm.giveMeBuilder(BillingKeyDto.class)
          .set("card", fm.giveMeOne(CardDto.class))
          .set("customerKey", requestDto.customerKey())
          .sample();

      given(client.getBillingKey(any())).willReturn(response);
      given(cardRepository.existsByNumber(any())).willReturn(true);

      // when
      assertThrows(CustomCardException.class, () -> cardService.createBillingKey(requestDto));

      // then
      then(cardRepository).should(never()).save(any());
      then(billingKeyRepository).should(never()).save(any());
    }

    @DisplayName("should throw exception when user is invalid")
    @Test
    void shouldThrowExceptionWhenUserIsInvalid() {
      // given
      BillingKeyRequestDto requestDto = fmRecord.giveMeOne(BillingKeyRequestDto.class);
      BillingKeyDto response = fm.giveMeBuilder(BillingKeyDto.class)
          .set("card", fm.giveMeOne(CardDto.class))
          .set("customerKey", "invalid_key")
          .sample();

      given(client.getBillingKey(any())).willReturn(response);

      // when
      assertThrows(CustomCardException.class, () -> cardService.createBillingKey(requestDto));

      // then
      then(cardRepository).should(never()).save(any());
      then(billingKeyRepository).should(never()).save(any());
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
