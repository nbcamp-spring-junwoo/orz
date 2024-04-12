package com.junwoo.ott.global.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ResponseDto<T> {

  private T data;

  public static <T> ResponseDto<T> ok(final T data) {
    return new ResponseDto<>(data);
  }

}
