package com.junwoo.ott.domain.video.dto.body;

import com.junwoo.ott.global.customenum.RatingType;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoUpdateDto {

  private final String title;
  private final String description;
  private final RatingType ratingType;

}
