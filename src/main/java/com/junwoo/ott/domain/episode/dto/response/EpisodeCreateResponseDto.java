package com.junwoo.ott.domain.episode.dto.response;

import com.junwoo.ott.domain.episode.entity.Episode;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class EpisodeCreateResponseDto {

  private final Long episodeId;
  private final Long videoId;
  private final String title;
  private final String description;
  private final LocalDate releasedAt;

  public EpisodeCreateResponseDto(final Episode episode) {
    this.episodeId = episode.getEpisodeId();
    this.videoId = episode.getVideoId();
    this.title = episode.getTitle();
    this.description = episode.getDescription();
    this.releasedAt = episode.getReleasedAt();
  }
}
