package com.junwoo.ott.domain.video.dto.request;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class VideoRequestDto {

  private final Pageable pageable;

  public VideoRequestDto(final Pageable pageable) {
    this.pageable = pageable;
  }

}
