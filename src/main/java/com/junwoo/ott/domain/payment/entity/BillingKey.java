package com.junwoo.ott.domain.payment.entity;

import com.junwoo.ott.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
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
public class BillingKey {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long billingKeyId;
  @Column(nullable = false, unique = true)
  private String key;
  @Column(nullable = false)
  private String customerKey;

  @OneToOne
  private Card card;

  @ManyToOne
  @JoinColumn(name = "user_id")
  private User user;

  public void setParents(final User user, final Card card) {
    this.user = user;
    this.card = card;
  }

}
