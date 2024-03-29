package com.junwoo.ott.domain.auth.dto.body;


import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
public class AuthSignupDto {

  @NotNull
  private String username;
  @NotNull
  private String password;
  @NotNull
  private String email;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private String born;
}
