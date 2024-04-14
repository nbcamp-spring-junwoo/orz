package com.junwoo.ott.domain.video.dto.response;

import com.junwoo.ott.global.customenum.MembershipType;
import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class VideoProjectionDto {

  private final Long videoId;
  private final String title;
  private final String description;
  private final String posterUrl;
  private final LocalDate releasedAt;
  private final MembershipType membershipType;

  @QueryProjection
  public VideoProjectionDto(
      final Long videoId,
      final String title,
      final String description,
      final String posterUrl,
      final LocalDate releasedAt,
      final MembershipType membershipType
  ) {
    this.videoId = videoId;
    this.title = title;
    this.description = description;
    this.posterUrl = posterUrl;
    this.releasedAt = releasedAt;
    this.membershipType = membershipType;
  }

}
