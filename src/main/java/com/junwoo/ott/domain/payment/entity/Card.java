package com.junwoo.ott.domain.payment.entity;

import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.common.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Card extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @Column(nullable = false, length = 16)
  private String cardNumber;
  @Column(nullable = false, length = 2)
  private String cardExpirationYear;
  @Column(nullable = false, length = 2)
  private String cardExpirationMonth;
  @Column(nullable = false, length = 6)
  private LocalDate customerIdentityNumber;
  @Column(nullable = false, length = 2)
  private String cardPassword;
  private String customerName;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  @Setter
  private User user;

}
