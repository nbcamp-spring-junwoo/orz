package com.junwoo.ott.domain.video.dto.response;

import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.global.customenum.RatingType;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoUpdateResponseDto {
  private final Long videoId;
  private final String title;
  private final String description;
  private final RatingType ratingType;
  private final Set<Long> categoryIds;

  public VideoUpdateResponseDto(final Video video, final Set<Long> categoryIds) {
    this.videoId = video.getVideoId();
    this.title = video.getTitle();
    this.description = video.getDescription();
    this.ratingType = video.getRatingType();
    this.categoryIds = categoryIds;
  }

}
