package com.junwoo.ott.domain.video.dto.request;

import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class VideoReadRequestDto {

  private final Pageable pageable;
  private final Long videoId;

  public VideoReadRequestDto(final Pageable pageable, final Long videoId) {
    this.pageable = pageable;
    this.videoId = videoId;
  }

}
