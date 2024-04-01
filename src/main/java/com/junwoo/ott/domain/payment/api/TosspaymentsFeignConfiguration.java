package com.junwoo.ott.domain.payment.api;

import com.junwoo.ott.global.properties.TosspaymentsProperties;
import com.nimbusds.common.contenttype.ContentType;
import feign.Logger;
import feign.RequestInterceptor;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;

@RequiredArgsConstructor
public class TosspaymentsFeignConfiguration {

  private final TosspaymentsProperties properties;

  @Bean
  public RequestInterceptor requestInterceptor() {
    Base64.Encoder encoder = Base64.getEncoder();
    byte[] encodedSecretKey = encoder.encode(
        (properties.getSecretKey() + ":").getBytes(StandardCharsets.UTF_8)
    );
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
