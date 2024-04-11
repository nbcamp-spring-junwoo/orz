package com.junwoo.ott.domain.video.dto.response;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoSearchResponseDto {

  private List<VideoReadResponseDto> videoReadResponseDtoList;
  private Long totalSize;
  private Long totalPage;

}
