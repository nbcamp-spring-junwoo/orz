package com.junwoo.ott.domain.payment.dto.remote;

import com.junwoo.ott.domain.payment.dto.remote.payment.CardDto;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BillingDto {

  // 상점아이디(MID)입니다. 토스페이먼츠에서 발급합니다. 최대 길이는 14자입니다.
  private String mId;
  // 구매자 ID입니다. 빌링키와 연결됩니다. 다른 사용자가 이 값을 알게 되면 악의적으로 사용될 수 있습니다.
  private String customerKey;
  // 결제수단이 인증된 시점의 날짜와 시간 정보입니다.
  private String authenticatedAt;
  // 결제수단입니다. 현재 자동결제는 카드만 지원하기 때문에 카드로 값이 고정되어 돌아옵니다.
  private String method;
  // 자동결제에서 카드 정보 대신 사용되는 값입니다. customerKey와 연결됩니다. 최대 길이는 200자입니다.
  private String billingKey;
  // 발급된 빌링키와 연결된 카드 정보입니다.
  private CardDto card;

  public Subscription toEntity() {
    return Subscription.builder()
        .customerKey(customerKey)
        .authenticatedAt(LocalDateTime.parse(authenticatedAt, DateTimeFormatter.ISO_OFFSET_DATE_TIME))
        .billingKey(billingKey)
        .build();
  }

}
