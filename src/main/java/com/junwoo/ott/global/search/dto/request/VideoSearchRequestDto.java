package com.junwoo.ott.global.search.dto.request;

import lombok.Getter;

@Getter
public class VideoSearchRequestDto {

  private final String input;
  private final Integer page;

  public VideoSearchRequestDto(String input, Integer page) {
    this.input = input;
    this.page = page == null ? 1 : page;
  }

}
