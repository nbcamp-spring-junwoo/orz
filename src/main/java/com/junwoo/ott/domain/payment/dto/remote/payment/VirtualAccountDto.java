package com.junwoo.ott.domain.payment.dto.remote.payment;

import com.junwoo.ott.global.customenum.payment.common.SettlementStatusType;
import com.junwoo.ott.global.customenum.payment.virtualaccount.RefundStatusType;
import com.junwoo.ott.global.customenum.payment.virtualaccount.VirtualAccountType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VirtualAccountDto {

  // 가상계좌 타입을 나타냅니다. 일반, 고정 중 하나입니다.
  private VirtualAccountType accountType;
  // 발급된 계좌번호입니다. 최대 길이는 20자입니다.
  private String accountNumber;
  // 가상계좌 은행 숫자 코드입니다. 은행 코드와 증권사 코드를 참고하세요.
  private String bankCode;
  // 가상계좌를 발급한 구매자명입니다. 최대 길이는 100자입니다.
  private String customerName;
  // 입금 기한입니다.
  private String dueDate;
  // 환불 처리 상태입니다. 아래와 같은 상태 값을 가질 수 있습니다.
  private RefundStatusType refundStatus;
  // 가상계좌의 만료 여부입니다.
  private Boolean expired;
  // 정산 상태입니다. 정산이 아직 되지 않았다면 INCOMPLETED, 정산이 완료됐다면 COMPLETED 값이 들어옵니다.
  private SettlementStatusType settlementStatus;
  // 결제위젯 가상계좌 환불 정보 입력 기능으로 받은 고객의 환불 계좌 정보입니다.
  private RefundReceiveAccountDto refundReceiveAccount;

}
