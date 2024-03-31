package com.junwoo.ott.domain.payment.dto.body.payment;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 결제위젯 가상계좌 환불 정보 입력 기능으로 받은 고객의 환불 계좌 정보입니다.
 */
@Getter
@AllArgsConstructor
public class RefundReceiveAccountDto {

  // 환불 받을 계좌의 은행 코드입니다. 은행 코드와 증권사 코드를 참고하세요.
  private String bankCode;
  // 환불 받을 계좌의 계좌번호입니다. 최대 길이는 20자입니다.
  private String accountNumber;
  // 환불 받을 계좌의 예금주 정보입니다. 최대 길이는 100자입니다.
  private String holderName;

}
