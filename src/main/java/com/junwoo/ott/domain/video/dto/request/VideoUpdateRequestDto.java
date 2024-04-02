package com.junwoo.ott.domain.video.dto.request;

import com.junwoo.ott.domain.video.dto.body.VideoUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoUpdateRequestDto {

  private final Long videoId;
  private final VideoUpdateDto dto;

}
