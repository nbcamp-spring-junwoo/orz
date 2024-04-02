package com.junwoo.ott.domain.payment.util;

import java.util.UUID;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = lombok.AccessLevel.PRIVATE)
public class CustomerKeyGenerator {

  public static String generateCustomerKey() {
    return UUID.randomUUID().toString() + '.' + UUID.randomUUID();
  }

}
