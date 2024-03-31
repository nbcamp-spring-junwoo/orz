package com.junwoo.ott.domain.video.dto.body;

import com.junwoo.ott.global.customenum.RatingType;
import lombok.Getter;

@Getter
public class VideoUpdateDto {

  private final String title;
  private final String description;
  private final RatingType ratingType;

  public VideoUpdateDto(String title, String description, RatingType ratingType) {
    this.title = title;
    this.description = description;
    this.ratingType = ratingType;
  }

}
