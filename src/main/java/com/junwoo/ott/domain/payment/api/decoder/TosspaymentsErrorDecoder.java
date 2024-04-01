package com.junwoo.ott.domain.payment.api.decoder;

import static java.nio.charset.StandardCharsets.UTF_8;

import com.junwoo.ott.global.exception.custom.CustomPaymentException;
import feign.Response;
import feign.codec.ErrorDecoder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.antlr.v4.runtime.CharStreams;
import org.springframework.http.HttpStatus;

@Slf4j(topic = "TosspaymentsErrorDecoder")
public class TosspaymentsErrorDecoder implements ErrorDecoder {

  @Override
  public Exception decode(final String methodKey, final Response response) {
    try {
      String body = CharStreams.fromReader(response.body().asReader(UTF_8)).toString();

      return new CustomPaymentException(body, HttpStatus.valueOf(response.status()));
    } catch (IOException e) {
      return new CustomPaymentException("Unknown error", HttpStatus.I_AM_A_TEAPOT);
    }
  }

}
