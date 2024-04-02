package com.junwoo.ott.domain.user.dto.request;

import com.junwoo.ott.domain.user.dto.body.UserPutDto;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserPutRequestDto {

  private Long requestId;
  private Long userId;
  private String checkedPassword;
  private String newPassword;
  private String newEmail;
  private String newBorn;


  public UserPutRequestDto(final Long requestId, final Long userId, final UserPutDto userPutDto) {
    this.requestId = requestId;
    this.userId = userId;
    this.checkedPassword = userPutDto.getCheckedPassword();
    this.newPassword = userPutDto.getNewPassword();
    this.newEmail = userPutDto.getNewEmail();
    this.newBorn = userPutDto.getNewBorn();
  }

}
