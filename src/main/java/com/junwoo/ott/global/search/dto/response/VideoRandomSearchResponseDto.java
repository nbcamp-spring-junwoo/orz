package com.junwoo.ott.global.search.dto.response;

import com.junwoo.ott.global.search.dto.body.VideoRandomSearchDto;
import com.junwoo.ott.global.search.dto.body.VideoSearchDto;
import java.util.List;
import lombok.Getter;

@Getter
public class VideoRandomSearchResponseDto {

  private final List<VideoRandomSearchDto> videos;

  public VideoRandomSearchResponseDto(List<VideoSearchDto> videos) {
    this.videos = videos.stream().map(VideoRandomSearchDto::new).toList();
  }

}
