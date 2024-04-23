package com.junwoo.ott.global.search.dto.body;

import lombok.Getter;

@Getter
public class VideoRandomDto {

  private final Long videoId;
  private final String title;
  private final String posterUrl;

  public VideoRandomDto(VideoDto videoSearchDto) {
    this.videoId = videoSearchDto.getVideo_id();
    this.title = videoSearchDto.getTitle();
    this.posterUrl = videoSearchDto.getPoster_url();
  }

}
