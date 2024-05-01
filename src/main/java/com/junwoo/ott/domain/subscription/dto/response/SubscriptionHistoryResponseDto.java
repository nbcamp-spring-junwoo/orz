package com.junwoo.ott.domain.subscription.dto.response;

import com.junwoo.ott.global.customenum.SubscriptionStatusType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class SubscriptionHistoryResponseDto {

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Long subscriptionHistoryId;
  private LocalDate expireAt;
  private SubscriptionStatusType status;
  private LocalDateTime cancelAt;

}
