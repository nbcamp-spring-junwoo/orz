package com.junwoo.ott.global.properties;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

@Data
@Validated
@Component
@Configuration
@ConfigurationProperties(prefix = "tosspayments")
public class TosspaymentsProperties {

  @NotNull
  private String url;
  @NotNull
  private String secretKey;
  @NotNull
  private String clientKey;

}
