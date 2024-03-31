package com.junwoo.ott.global.config;

import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Configuration;

@EnableFeignClients(basePackages = "com.junwoo.ott.domain.payment.api")
@Configuration
public class FeignConfiguration {

}
