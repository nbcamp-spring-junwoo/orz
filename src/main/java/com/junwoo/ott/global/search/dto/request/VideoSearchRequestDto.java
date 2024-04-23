package com.junwoo.ott.global.search.dto.request;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class VideoSearchRequestDto {

  private final String input;
  private final Pageable pageable;

  public VideoSearchRequestDto(String input, Pageable pageable) {
    this.input = input;
    this.pageable = pageable;
  }

}
