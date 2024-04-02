package com.junwoo.ott.domain.video.dto.body;


import com.junwoo.ott.global.customenum.RatingType;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoCreateDto {

  @NotNull
  private final String title;
  @NotNull
  private final String description;
  @NotNull
  private final RatingType ratingType;

}
