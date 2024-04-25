package com.junwoo.ott.global.search.dto.request;

import com.junwoo.ott.global.customenum.SearchType;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class VideoSearchRequestDto {

  private final String input;
  private final Pageable pageable;
  private final SearchType searchType;

  public VideoSearchRequestDto(
      final String input, final Pageable pageable, final SearchType searchType
  ) {
    this.input = input;
    this.pageable = pageable;
    this.searchType = searchType;
  }

}
