package com.junwoo.ott.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@EnableWebMvc
@Configuration
public class CorsConfiguration implements WebMvcConfigurer {

  @Override
  public void addCorsMappings(final CorsRegistry registry) {
    registry
        .addMapping("/**")
        .allowedMethods("*")
        .allowCredentials(true)
        .allowedOriginPatterns(
            "http://localhost:*",
            "https://localhost:*",

            "http://*.junwoo.com",
            "https://*.junwoo.com"
        )


        .exposedHeaders("Authorization");
  }

}
