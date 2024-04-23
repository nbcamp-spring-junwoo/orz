package com.junwoo.ott.global.search.dto.body;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.junwoo.ott.global.customenum.MembershipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoDto {

  private Long video_id;
  private String title;
  private String poster_url;
  private MembershipType membership_type;

}
