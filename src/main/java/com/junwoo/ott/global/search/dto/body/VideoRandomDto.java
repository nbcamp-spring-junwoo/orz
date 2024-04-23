package com.junwoo.ott.global.search.dto.body;

import com.junwoo.ott.global.search.dto.response.VideoResponseDto;
import lombok.Getter;

@Getter
public class VideoRandomDto {

  private final Long videoId;
  private final String title;
  private final String posterUrl;

  public VideoRandomDto(VideoResponseDto videoSearchDto) {
    this.videoId = videoSearchDto.getVideo_id();
    this.title = videoSearchDto.getTitle();
    this.posterUrl = videoSearchDto.getPoster_url();
  }

}
