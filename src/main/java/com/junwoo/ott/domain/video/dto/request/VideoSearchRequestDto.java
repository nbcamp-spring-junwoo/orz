package com.junwoo.ott.domain.video.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoSearchRequestDto {

  private String title;
  private int page;

}
