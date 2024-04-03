package com.junwoo.ott.domain.payment.util;

import com.junwoo.ott.global.customenum.payment.OrderType;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class PaymentDataGenerator {

  public static String generateCustomerKey() {
    return UUID.randomUUID().toString() + '.' + UUID.randomUUID();
  }

  public static String generateOrderId(final OrderType type) {
    // 6 자리
    String now = LocalDate.now().format(DateTimeFormatter.ofPattern("yyMMdd"));
    // 2 자리
    String paymentType = type.getCode();
    // 8 자리
    String randomUUID = UUID.randomUUID().toString().substring(0, 8);

    return now + paymentType + randomUUID;
  }

}
