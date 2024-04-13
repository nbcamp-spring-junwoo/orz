package com.junwoo.ott.global.search.dto.response;

import com.junwoo.ott.global.search.dto.body.VideoSearchDto;
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
