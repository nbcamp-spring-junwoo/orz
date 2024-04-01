package com.junwoo.ott.global.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "tosspayments")
public class TosspaymentsProperties {

  private String url;
  private String secretKey;
  private String clientKey;

}
