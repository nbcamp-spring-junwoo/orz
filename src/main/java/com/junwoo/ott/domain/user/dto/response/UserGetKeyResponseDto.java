package com.junwoo.ott.domain.user.dto.response;

import lombok.Getter;

@Getter
public class UserGetKeyResponseDto {

  private final String customerKey;

  public UserGetKeyResponseDto(String customerKey) {
    this.customerKey = customerKey;
  }

}
