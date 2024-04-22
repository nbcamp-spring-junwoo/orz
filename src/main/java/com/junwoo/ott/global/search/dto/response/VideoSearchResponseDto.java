package com.junwoo.ott.global.search.dto.response;

import com.junwoo.ott.global.search.dto.body.VideoSearchDto;
import com.junwoo.ott.global.search.dto.body.VideoSearchResultDto;
import java.util.List;
import lombok.Getter;

@Getter
public class VideoSearchResponseDto {

  private final List<VideoSearchResultDto> videos;
  private final Long totalSize;
  private final Long totalPage;

  public VideoSearchResponseDto(List<VideoSearchDto> videos, Long totalPage, Long totalSize) {
    this.videos = videos.stream().map(VideoSearchResultDto::new).toList();
    this.totalSize = totalSize;
    this.totalPage = totalPage;
  }

}
