package com.junwoo.ott.domain.video.dto.response;

import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.global.customenum.MembershipType;
import lombok.Getter;

@Getter
public class VideoCreateResponseDto {

  private final Long videoId;
  private final String title;
  private final String description;
  private final MembershipType membershipType;
  private final String posterUrl;

  public VideoCreateResponseDto(final Video video) {
    this.videoId = video.getVideoId();
    this.title = video.getTitle();
    this.membershipType = video.getMembershipType();
    this.description = video.getDescription();
    this.posterUrl = video.getPosterUrl();
  }

}
