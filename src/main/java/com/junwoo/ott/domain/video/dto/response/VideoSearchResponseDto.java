package com.junwoo.ott.domain.video.dto.response;

import com.junwoo.ott.domain.video.dto.body.VideoSearchDto;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoSearchResponseDto {

  private List<VideoSearchDto> videoReadResponseDtoList;
  private Long totalSize;
  private Long totalPage;

}
