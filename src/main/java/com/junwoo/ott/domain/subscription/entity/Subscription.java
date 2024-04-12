package com.junwoo.ott.domain.subscription.entity;


import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.domain.payment.entity.BillingKey;
import com.junwoo.ott.domain.payment.entity.Card;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.common.entity.Timestamped;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Subscription extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long subscriptionId;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "membership_id")
  private Membership membership;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "card_id")
  private Card card;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "billing_key_id")
  private BillingKey billingKey;

  public void setParents(
      final User user,
      final Card card,
      final Membership membership,
      final BillingKey billingKey
  ) {
    this.user = user;
    this.membership = membership;
    this.card = card;
    this.billingKey = billingKey;
  }

}
