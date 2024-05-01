package com.junwoo.ott.domain.episode.dto.response;

import com.querydsl.core.annotations.QueryProjection;
import java.time.LocalDate;
import lombok.Getter;

@Getter
public class EpisodeProjectionDto {

  private final Long episodeId;
  private final String title;
  private final String description;
  private final String episodeUrl;
  private final LocalDate releasedAt;

  @QueryProjection
  public EpisodeProjectionDto(
      final Long episodeId, final String title,
      final String description, final String episodeUrl, final LocalDate releasedAt
  ) {
    this.episodeId = episodeId;
    this.title = title;
    this.description = description;
    this.episodeUrl = episodeUrl;
    this.releasedAt = releasedAt;
  }

}
