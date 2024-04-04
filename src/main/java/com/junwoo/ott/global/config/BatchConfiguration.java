package com.junwoo.ott.global.config;

import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BatchConfiguration {

  @Bean
  public static BeanDefinitionRegistryPostProcessor jobRegistryBeanPostProcessorRemover() {
    return registry -> registry.removeBeanDefinition("jobRegistryBeanPostProcessor");
  }

}
