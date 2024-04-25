package com.junwoo.ott.global.search.dto.response;

import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.search.dto.body.VideoDto;
import lombok.Getter;

@Getter
public class VideoResponseDto {

  private final Long videoId;
  private final String title;
  private final String posterUrl;
  private final MembershipType membershipType;

  public VideoResponseDto(final VideoDto videoDto) {
    this.videoId = videoDto.getVideo_id();
    this.title = videoDto.getTitle();
    this.posterUrl = videoDto.getPoster_url();
    this.membershipType = videoDto.getMembership_type();
  }

}
