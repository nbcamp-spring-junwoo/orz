package com.junwoo.ott.domain.user.dto.reponse;

import com.junwoo.ott.global.customenum.AuthorityType;
import com.junwoo.ott.global.customenum.MembershipType;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserReadResponseDto {

  private Long userId;
  private String username;
  private String email;
  private LocalDate born;
  private AuthorityType authorityType;
  private MembershipType membershipType;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

}
