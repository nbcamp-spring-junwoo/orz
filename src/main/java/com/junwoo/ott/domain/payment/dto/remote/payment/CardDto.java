package com.junwoo.ott.domain.payment.dto.remote.payment;

import com.junwoo.ott.global.customenum.payment.card.AcquireStatusType;
import com.junwoo.ott.global.customenum.payment.card.CardOwnerType;
import com.junwoo.ott.global.customenum.payment.card.CardType;
import com.junwoo.ott.global.customenum.payment.card.InterestPayerType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 카드로 결제하면 제공되는 카드 관련 정보입니다.
 */
@Getter
@AllArgsConstructor
public class CardDto {

  // 카드사에 결제 요청한 금액입니다. 즉시 할인 금액(discount.amount)이 포함됩니다.
  private Integer amount;
  // 카드 발급사 숫자 코드입니다. 카드사 코드를 참고하세요.
  private String issuerCode;
  // 카드 매입사 숫자 코드입니다. 카드사 코드를 참고하세요.
  private String acquirerCode;
  // 카드번호입니다. 번호의 일부는 마스킹 되어 있습니다. 최대 길이는 20자입니다.
  private String number;
  // 할부 개월 수입니다. 일시불이면 0입니다.
  private Integer installmentPlanMonths;
  // 카드사 승인 번호입니다. 최대 길이는 8자입니다.
  private String approveNo;
  // 카드사 포인트 사용 여부입니다.
  private Boolean useCardPoint;
  // 카드 종류입니다.
  private CardType cardType;
  // 카드의 소유자 타입입니다.
  private CardOwnerType ownerType;
  // 카드 결제의 매입 상태입니다.
  private AcquireStatusType acquireStatus;
  // 무이자 할부의 적용 여부입니다.
  private Boolean isInterestFree;
  // 할부가 적용된 결제에서 할부 수수료를 부담하는 주체입니다.
  private InterestPayerType interestPayer;

}
