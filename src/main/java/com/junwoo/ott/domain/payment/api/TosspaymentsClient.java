package com.junwoo.ott.domain.payment.api;

import com.junwoo.ott.domain.payment.api.configuration.TosspaymentsFeignConfiguration;
import com.junwoo.ott.domain.payment.dto.remote.BillingKeyDto;
import com.junwoo.ott.domain.payment.dto.remote.BillingKeyRequestByAuthKeyDto;
import com.junwoo.ott.domain.payment.dto.remote.PaymentDto;
import com.junwoo.ott.domain.payment.dto.request.BillingConfirmRequestDto;
import com.junwoo.ott.domain.payment.dto.request.BillingKeyRequestDto;
import com.junwoo.ott.domain.payment.dto.request.PaymentConfirmRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "tosspayments", url = "${tosspayments.url}", configuration = TosspaymentsFeignConfiguration.class)
public interface TosspaymentsClient {

  @PostMapping(value = "/v1/payments/confirm")
  PaymentDto confirmPayment(PaymentConfirmRequestDto requestDto);

  @PostMapping(value = "/v1/billing/authorizations/card")
  BillingKeyDto getBillingKey(BillingKeyRequestDto requestDto);

  @PostMapping(value = "/v1/billing/authorizations/issue")
  BillingKeyDto getBillingKey(BillingKeyRequestByAuthKeyDto requestDto);

  @PostMapping(value = "/v1/billing/{billingKey}")
  PaymentDto confirmBilling(@PathVariable String billingKey, BillingConfirmRequestDto requestDto);

}
