package com.junwoo.ott.domain.auth.dto.body;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
@AllArgsConstructor
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
