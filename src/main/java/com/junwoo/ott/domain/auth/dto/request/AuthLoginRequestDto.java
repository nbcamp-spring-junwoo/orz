package com.junwoo.ott.domain.auth.dto.request;

import com.junwoo.ott.domain.auth.dto.body.AuthLoginDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthLoginRequestDto {

  private String username;
  private String password;

  public AuthLoginRequestDto(@Valid AuthLoginDto authLoginDto) {
    this.username = authLoginDto.getUsername();
    this.password = authLoginDto.getPassword();
  }

}
