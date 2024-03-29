package com.junwoo.ott.domain.video.dto.request;

import com.junwoo.ott.domain.video.dto.body.VideoCreateDto;
import com.junwoo.ott.global.customenum.RatingType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoCreateRequestDto {

  private String title;
  private String description;
  private RatingType ratingType;

  public VideoCreateRequestDto(VideoCreateDto dto) {
    this.title = dto.getTitle();
    this.description = dto.getDescription();
    this.ratingType = dto.getRatingType();
  }

}
