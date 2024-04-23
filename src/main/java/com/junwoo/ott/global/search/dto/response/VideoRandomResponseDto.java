package com.junwoo.ott.global.search.dto.response;

import com.junwoo.ott.global.search.dto.body.VideoRandomDto;
import java.util.List;
import lombok.Getter;

@Getter
public class VideoRandomResponseDto {

  private final List<VideoRandomDto> videos;

  public VideoRandomResponseDto(List<VideoResponseDto> videos) {
    this.videos = videos.stream().map(VideoRandomDto::new).toList();
  }

}
