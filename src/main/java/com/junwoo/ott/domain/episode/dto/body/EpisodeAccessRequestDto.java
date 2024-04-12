package com.junwoo.ott.domain.episode.dto.body;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EpisodeAccessRequestDto {

  private final Long videoId;
  private final Long episodeId;

}
