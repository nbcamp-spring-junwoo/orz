package com.junwoo.ott.domain.payment.dto.remote.payment;


import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 결제 승인에 실패하면 응답으로 받는 에러 객체입니다. 실패한 결제를 조회할 때 확인할 수 있습니다.
 */
@Getter
@AllArgsConstructor
public class FailureDto {

  // 오류 타입을 보여주는 에러 코드입니다.
  private String code;
  // 에러 메시지입니다. 에러 발생 이유를 알려줍니다. 최대 길이는 510자입니다.
  private String message;

}
