package com.junwoo.ott.domain.auth.dto.reponse;

import com.junwoo.ott.domain.auth.dto.body.AuthSignupDto;
import com.junwoo.ott.domain.user.entity.User;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class AuthSignupRequestDto {

  private final String username;
  private final String password;
  private final String email;
  private final String born;

  public AuthSignupRequestDto(AuthSignupDto authSignupDto) {
    this.username = authSignupDto.getUsername();
    this.password = authSignupDto.getPassword();
    this.email = authSignupDto.getEmail();
    this.born = authSignupDto.getBorn();
  }

  public User authSignupRequestDtoToUser(String encodedPassword, LocalDate datedBorn) {
    return User.builder()
        .username(username)
        .password(encodedPassword)
        .email(email)
        .born(datedBorn)
        .build();
  }

}
