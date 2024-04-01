package com.junwoo.ott.domain.auth.dto.body;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthAdminSignupDto {

  @NotNull
  private String username;
  @NotNull
  private String password;
  @NotNull
  private String adminKey;

}
