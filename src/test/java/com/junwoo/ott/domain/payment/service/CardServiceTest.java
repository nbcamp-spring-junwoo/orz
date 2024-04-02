package com.junwoo.ott.domain.payment.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.then;

import com.junwoo.ott.domain.payment.dto.request.CardCreateRequestDto;
import com.junwoo.ott.domain.payment.repository.CardRepository;
import com.navercorp.fixturemonkey.FixtureMonkey;
import com.navercorp.fixturemonkey.api.introspector.ConstructorPropertiesArbitraryIntrospector;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith({MockitoExtension.class})
class CardServiceTest {

  @Mock
  private CardRepository cardRepository;
  @InjectMocks
  private CardService cardService;
  private final FixtureMonkey fm = FixtureMonkey.builder()
      .objectIntrospector(ConstructorPropertiesArbitraryIntrospector.INSTANCE)
      .build();

  @Nested
  @DisplayName("카드 생성")
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

}
