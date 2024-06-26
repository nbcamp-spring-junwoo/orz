package com.junwoo.ott.domain.subscription.entity;

import com.junwoo.ott.domain.payment.entity.Order;
import com.junwoo.ott.domain.subscription.dto.response.SubscriptionHistoryResponseDto;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.common.entity.Timestamped;
import com.junwoo.ott.global.customenum.SubscriptionStatusType;
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
import jakarta.persistence.OneToOne;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class SubscriptionHistory extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long subscriptionHistoryId;
  @Column(nullable = false)
  private LocalDate expireAt;
  @Default
  @Column(nullable = false)
  @Enumerated(value = EnumType.STRING)
  private SubscriptionStatusType status = SubscriptionStatusType.ACTIVE;
  private LocalDateTime cancelAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "subscription_id")
  private Subscription subscription;

  @OneToOne
  @JoinColumn(name = "order_id")
  private Order order;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;

  public void setParents(final Subscription subscription, final Order order) {
    this.subscription = subscription;
    this.order = order;
    this.user = subscription.getUser();
  }

  public SubscriptionHistoryResponseDto toResponseDto() {
    return SubscriptionHistoryResponseDto.builder()
        .createdAt(getCreatedAt())
        .updatedAt(getUpdatedAt())
        .subscriptionHistoryId(subscriptionHistoryId)
        .expireAt(expireAt)
        .status(status)
        .cancelAt(cancelAt)
        .build();
  }

}
