package com.junwoo.ott.global.search.dto.body;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoSearchResultDto {

  private Long videoId;
  private String title;
  private String description;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDateTime releasedAt;
  private String posterUrl;

  public VideoSearchResultDto(VideoSearchDto videoSearchDto) {
    this.videoId = videoSearchDto.getVideo_id();
    this.title = videoSearchDto.getTitle();
    this.description = videoSearchDto.getDescription();
    this.releasedAt = videoSearchDto.getReleased_at();
    this.posterUrl = videoSearchDto.getPoster_url();
  }

}
