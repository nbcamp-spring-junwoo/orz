package com.junwoo.ott.global.search.dto.response;

import com.junwoo.ott.global.search.dto.body.VideoDto;
import com.junwoo.ott.global.search.dto.body.VideoRandomDto;
import java.util.List;
import lombok.Getter;

@Getter
public class VideoRandomResponseDto {

  private final List<VideoRandomDto> videos;

  public VideoRandomResponseDto(final List<VideoDto> videos) {
    this.videos = videos.stream().map(VideoRandomDto::new).toList();
  }

}
