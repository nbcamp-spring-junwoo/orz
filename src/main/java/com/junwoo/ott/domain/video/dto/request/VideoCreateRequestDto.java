package com.junwoo.ott.domain.video.dto.request;

import com.junwoo.ott.domain.video.dto.body.VideoCreateDto;
import com.junwoo.ott.global.customenum.RatingType;
import lombok.Getter;

@Getter
public class VideoCreateRequestDto {

  private final String title;
  private final String description;
  private final RatingType ratingType;
  private final String videoUrl;

  public VideoCreateRequestDto(final VideoCreateDto videoCreateDto) {
    this.title = videoCreateDto.getTitle();
    this.description = videoCreateDto.getDescription();
    this.ratingType = videoCreateDto.getRatingType();
    this.videoUrl = videoCreateDto.getVideoUrl();
  }

}
