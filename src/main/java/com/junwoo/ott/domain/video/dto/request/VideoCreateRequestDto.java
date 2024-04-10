package com.junwoo.ott.domain.video.dto.request;

import com.junwoo.ott.domain.video.dto.body.VideoCreateDto;
import com.junwoo.ott.global.customenum.RatingType;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoCreateRequestDto {

  private final String title;
  private final String description;
  private final RatingType ratingType;
  private final Set<Long> categoryIds;

  public VideoCreateRequestDto(final VideoCreateDto dto) {
    this.title = dto.getTitle();
    this.description = dto.getDescription();
    this.ratingType = dto.getRatingType();
    this.categoryIds = dto.getCategoryIds();
  }

}
