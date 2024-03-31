package com.junwoo.ott.domain.payment.api;

import com.nimbusds.common.contenttype.ContentType;
import feign.Logger;
import feign.RequestInterceptor;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

public class TosspaymentsFeignConfiguration {

  @Value("${tosspayments.secret-key}")
  private String secretKey;

  @Bean
  public RequestInterceptor requestInterceptor() {
    Base64.Encoder encoder = Base64.getEncoder();
    byte[] encodedSecretKey = encoder.encode((secretKey + ":").getBytes(StandardCharsets.UTF_8));
    String authorizations = "Basic " + new String(encodedSecretKey);

    return template -> {
      template.header("Content-Type", ContentType.APPLICATION_JSON.getType());
      template.header("Authorization", authorizations);
    };
  }

  @Bean
  Logger.Level feignLoggerLevel() {
    return Logger.Level.FULL;
  }

}
