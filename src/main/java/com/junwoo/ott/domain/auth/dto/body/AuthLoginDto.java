package com.junwoo.ott.domain.auth.dto.body;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class AuthLoginDto {

  @NotNull
  private String username;
  @NotNull
  private String password;

}
