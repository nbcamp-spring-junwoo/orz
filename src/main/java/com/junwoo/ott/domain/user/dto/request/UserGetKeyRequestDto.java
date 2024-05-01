package com.junwoo.ott.domain.user.dto.request;

import lombok.Getter;

@Getter
public class UserGetKeyRequestDto {

  private final Long userId;

  public UserGetKeyRequestDto(final Long userId) {
    this.userId = userId;
  }

}
