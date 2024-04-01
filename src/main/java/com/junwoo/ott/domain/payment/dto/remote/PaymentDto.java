package com.junwoo.ott.domain.payment.dto.remote;

import com.junwoo.ott.domain.payment.dto.remote.payment.CacheReceiptDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.CancelDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.CardDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.CheckoutDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.DiscountDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.EasyPayDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.FailureDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.GiftCertificateDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.MobilePhoneDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.ReceiptDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.TransferDto;
import com.junwoo.ott.domain.payment.dto.remote.payment.VirtualAccountDto;
import com.junwoo.ott.global.customenum.payment.PaymentStatusType;
import com.junwoo.ott.global.customenum.payment.PaymentType;
import com.junwoo.ott.global.customenum.payment.common.PaymentMethodType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 결제 정보를 담고 있는 객체입니다. 결제 한 건의 결제 상태, 결제 취소 기록, 매출 전표, 현금영수증 정보 등을 자세히 알 수 있습니다. 결제가 승인됐을 때 응답은
 * Payment 객체로 항상 동일합니다. 객체의 구성은 결제수단(카드, 가상계좌, 간편결제 등)에 따라 조금씩 달라집니다.
 */
@Getter
@AllArgsConstructor
public class PaymentDto {

  // Payment 객체의 응답 버전입니다. 버전 2022-06-08부터 날짜 기반 버저닝을 사용합니다.
  private String version;
  // 결제의 키 값입니다. 최대 길이는 200자입니다. 결제를 식별하는 역할로, 중복되지 않는 고유한 값입니다. 결제 데이터 관리를 위해 반드시 저장해야 합니다. 결제 상태가 변해도 값이 유지됩니다. 결제 승인, 결제 조회, 결제 취소 API에 사용합니다.
  private String paymentKey;
  // 결제 타입 정보입니다. NORMAL(일반결제), BILLING(자동결제), BRANDPAY(브랜드페이) 중 하나입니다.
  private PaymentType type;
  // 주문번호입니다. 최소 길이는 6자, 최대 길이는 64자입니다.
  // 주문한 결제를 식별하는 역할로, 결제를 요청할 때 가맹점에서 만들어서 사용한 값입니다.
  // 결제 데이터 관리를 위해 반드시 저장해야 합니다. 중복되지 않는 고유한 값을 발급해야 합니다. 결제 상태가 변해도 값이 유지됩니다.
  private String orderId;
  // 구매상품입니다. 예를 들면 생수 외 1건 같은 형식입니다. 최대 길이는 100자입니다.
  private String orderName;
  // 상점아이디(MID)입니다. 토스페이먼츠에서 발급합니다. 최대 길이는 14자입니다.
  private String mId;
  // 결제할 때 사용한 통화입니다.
  private PaymentMethodType currency;
  // 결제수단입니다. 카드, 가상계좌, 간편결제, 휴대폰, 계좌이체, 문화상품권, 도서문화상품권, 게임문화상품권 중 하나입니다.
  private PaymentMethodType method;
  // 총 결제 금액입니다. 결제가 취소되는 등 결제 상태가 변해도 최초에 결제된 결제 금액으로 유지됩니다.
  private Integer totalAmount;
  // 취소할 수 있는 금액(잔고)입니다. 이 값은 결제 취소나 부분 취소가 되고 나서 남은 값입니다.
  // 결제 상태 변화에 따라 vat, suppliedAmount, taxFreeAmount, taxExemptionAmount와 함께 값이 변합니다.
  private Integer balanceAmount;
  // 결제 처리 상태입니다.
  private PaymentStatusType status;
  // 결제가 일어난 날짜와 시간 정보입니다.
  private String requestedAt;
  // 결제 승인이 일어난 날짜와 시간 정보입니다.
  private String approvedAt;
  // 에스크로 사용 여부입니다.
  private Boolean useEscrow;
  // 마지막 거래의 키 값입니다. 한 결제 건의 승인 거래와 취소 거래를 구분하는 데 사용됩니다.
  private String lastTransactionKey;
  // 공급가액입니다. 결제 취소 및 부분 취소가 되면 공급가액도 일부 취소되어 값이 바뀝니다.
  private Integer suppliedAmount;
  // 부가세입니다. 결제 취소 및 부분 취소가 되면 부가세도 일부 취소되어 값이 바뀝니다.
  private Integer vat;
  // 문화비(도서, 공연 티켓, 박물관·미술관 입장권 등) 지출 여부입니다.
  private Boolean cultureExpense;
  // 결제 금액 중 면세 금액입니다. 결제 취소 및 부분 취소가 되면 면세 금액도 일부 취소되어 값이 바뀝니다.
  private Integer taxFreeAmount;
  // 과세를 제외한 결제 금액(컵 보증금 등)입니다.
  private Integer taxExemptionAmount;
  // 결제 취소 이력입니다. Cancel 객체가 배열로 들어옵니다.
  private List<CancelDto> cancels;
  // 부분 취소 가능 여부입니다.
  private Boolean isPartialCancelable;
  // 카드로 결제하면 제공되는 카드 관련 정보입니다.
  private CardDto card;
  // 가상계좌로 결제하면 제공되는 가상계좌 관련 정보입니다.
  private VirtualAccountDto virtualAccount;
  // 가상계좌 웹훅이 정상적인 요청인지 검증하는 값입니다.
  private String secret;
  // 휴대폰으로 결제하면 제공되는 휴대폰 결제 관련 정보입니다.
  private MobilePhoneDto mobilePhone;
  // 상품권으로 결제하면 제공되는 상품권 결제 관련 정보입니다.
  private GiftCertificateDto giftCertificate;
  // 계좌이체로 결제했을 때 이체 정보가 담기는 객체입니다.
  private TransferDto transfer;
  // 발행된 영수증 정보입니다.
  private ReceiptDto receipt;
  // 결제창 정보입니다.
  private CheckoutDto checkout;
  // 간편결제 정보입니다. 고객이 선택한 결제수단에 따라 amount, discountAmount가 달라집니다. 간편결제 응답 확인 가이드를 참고하세요.
  private EasyPayDto easyPay;
  // 결제한 국가입니다. ISO-3166의 두 자리 국가 코드 형식입니다.
  private String country;
  // 결제 승인에 실패하면 응답으로 받는 에러 객체입니다. 실패한 결제를 조회할 때 확인할 수 있습니다.
  private FailureDto failure;
  // 현금영수증 정보입니다.
  private CacheReceiptDto cashReceipt;
  // 현금영수증 발행 및 취소 이력이 담기는 배열입니다.
  private List<CacheReceiptDto> cashReceipts;
  // 카드사의 즉시 할인 프로모션 정보입니다. 즉시 할인 프로모션이 적용됐을 때만 생성됩니다.
  private DiscountDto discount;

}
