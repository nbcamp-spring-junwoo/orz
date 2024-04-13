package com.junwoo.ott.domain.video.dto.response;

import com.junwoo.ott.global.customenum.MembershipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoResponseDto {

  private Long videoId;
  private String title;
  private String description;
  private String posterUrl;
  private MembershipType membershipType;

}
