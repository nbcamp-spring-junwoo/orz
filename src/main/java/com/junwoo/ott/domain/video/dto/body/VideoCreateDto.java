package com.junwoo.ott.domain.video.dto.body;

import com.junwoo.ott.global.customenum.RatingType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoCreateDto {

  @NotNull
  private String title;
  @NotNull
  private String description;
  @NotNull
  private RatingType ratingType;
  @NotNull
  private String videoUrl;

}
