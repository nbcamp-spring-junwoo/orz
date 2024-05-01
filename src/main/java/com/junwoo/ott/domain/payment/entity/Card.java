package com.junwoo.ott.domain.payment.entity;

import com.junwoo.ott.domain.payment.dto.response.CardResponseDto;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.common.entity.Timestamped;
import com.junwoo.ott.global.customenum.payment.card.CardOwnerType;
import com.junwoo.ott.global.customenum.payment.card.CardType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Card extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long cardId;
  @Column(nullable = false, length = 2)
  private String issuerCode;
  @Column(nullable = false, length = 2)
  private String acquirerCode;
  @Column(nullable = false, length = 16)
  private String number;
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private CardType cardType;
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private CardOwnerType ownerType;
  @Column(length = 10)
  private String cardNickname;

  @PrePersist
  public void prePersist() {
    if (cardNickname == null) {
      cardNickname = "카드";
    }
  }

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public void setParents(final User user) {
    this.user = user;
  }

  public CardResponseDto toResponseDto() {
    return CardResponseDto.builder()
        .cardId(cardId)
        .cardNumber(number)
        .cardNickname(cardNickname)
        .build();
  }

}
