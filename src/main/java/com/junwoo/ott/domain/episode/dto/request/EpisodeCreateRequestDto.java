package com.junwoo.ott.domain.episode.dto.request;

import com.junwoo.ott.domain.episode.dto.body.EpisodeCreateDto;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeCreateRequestDto {

  private Long videoId;
  private String title;
  private String description;
  private LocalDate releasedAt;

  public EpisodeCreateRequestDto(final EpisodeCreateDto episodeCreateDto) {
    this.videoId = episodeCreateDto.getVideoId();
    this.title = episodeCreateDto.getTitle();
    this.description = episodeCreateDto.getDescription();
    this.releasedAt = LocalDate.parse(episodeCreateDto.getReleasedAt());
  }

}
