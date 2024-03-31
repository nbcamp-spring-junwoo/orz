package com.junwoo.ott.domain.user.dto.body;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@AllArgsConstructor
public class UserPutDto {

  @NotNull
  private String checkedPassword;
  private String newPassword;
  private String newEmail;
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private String newBorn;

}
