package com.junwoo.ott.domain.payment.dto.body.payment;

import com.junwoo.ott.global.customenum.payment.common.SettlementStatusType;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 상품권으로 결제하면 제공되는 상품권 결제 관련 정보입니다.
 */
@Getter
@AllArgsConstructor
public class GiftCertificateDto {

  // 결제 승인번호입니다. 최대 길이는 8자입니다.
  private String approveNo;
  // 정산 상태입니다. 정산이 아직 되지 않았다면 INCOMPLETED, 정산이 완료됐다면 COMPLETED 값이 들어옵니다.
  private SettlementStatusType settlementStatus;

}
