package com.junwoo.ott.domain.payment.dto.remote.payment;

import com.junwoo.ott.global.customenum.payment.common.SettlementStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;


/**
 * 계좌이체로 결제했을 때 이체 정보가 담기는 객체입니다.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class TransferDto {

  // 은행 숫자 코드입니다. 은행 코드와 증권사 코드를 참고하세요.
  private String bankCode;
  // 정산 상태입니다. 정산이 아직 되지 않았다면 INCOMPLETED, 정산이 완료됐다면 COMPLETED 값이 들어옵니다.
  private SettlementStatusType settlementStatus;

}
