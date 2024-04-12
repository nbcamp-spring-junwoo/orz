package com.junwoo.ott.domain.video.dto.response;

import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.global.customenum.RatingType;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoCreateResponseDto {

  private final Long videoId;
  private final String title;
  private final String description;
  private final RatingType ratingType;
  private final Set<Long> categoryIds;
  private final LocalDateTime createdAt;
  private final LocalDateTime updatedAt;

  public VideoCreateResponseDto(final Video video, final Set<Long> categoryIds) {
    this.videoId = video.getVideoId();
    this.categoryIds = categoryIds;
    this.title = video.getTitle();
    this.description = video.getDescription();
    this.ratingType = video.getRatingType();
    this.createdAt = video.getCreatedAt();
    this.updatedAt = video.getUpdatedAt();
  }

}
