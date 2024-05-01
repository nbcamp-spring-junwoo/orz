package com.junwoo.ott.domain.auth.dto.request;

import com.junwoo.ott.domain.admin.entity.Admin;
import com.junwoo.ott.domain.auth.dto.body.AuthAdminSignupDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class AuthAdminSignupRequestDto {

  private String username;
  private String password;
  private String adminKey;

  public AuthAdminSignupRequestDto(final AuthAdminSignupDto authAdminSignupDto) {
    this.username = authAdminSignupDto.getUsername();
    this.password = authAdminSignupDto.getPassword();
    this.adminKey = authAdminSignupDto.getAdminKey();
  }

  public Admin authAdminSignupRequestDtoToAdmin(
      final String reformedAdminName,
      final String encodedPassword
  ) {
    return Admin.builder()
        .username(reformedAdminName)
        .password(encodedPassword)
        .build();
  }

}
