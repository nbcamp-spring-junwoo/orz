package com.junwoo.ott.domain.payment.api;

import com.junwoo.ott.domain.payment.api.configuration.TosspaymentsFeignConfiguration;
import com.junwoo.ott.domain.payment.dto.body.PaymentConfirmDto;
import com.junwoo.ott.domain.payment.dto.remote.PaymentDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "tosspayments", url = "${tosspayments.url}", configuration = TosspaymentsFeignConfiguration.class)
public interface TosspaymentsClient {

  @PostMapping(value = "/v1/payments/confirm")
  PaymentDto confirmPayment(PaymentConfirmDto paymentConfirmDto);

}
