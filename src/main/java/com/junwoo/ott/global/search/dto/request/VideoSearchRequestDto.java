package com.junwoo.ott.global.search.dto.request;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class VideoSearchRequestDto {

  private final String input;
  private final Pageable pageable;
  private final String searchType;

  public VideoSearchRequestDto(
      final String input, final Pageable pageable, final String searchType
  ) {
    this.input = input;
    this.pageable = pageable;
    this.searchType = searchType;
  }

}
