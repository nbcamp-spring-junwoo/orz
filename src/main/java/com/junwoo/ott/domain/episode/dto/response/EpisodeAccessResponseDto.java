package com.junwoo.ott.domain.episode.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EpisodeAccessResponseDto {

  private final boolean canAccess;
  private final String videoLink;

  public static EpisodeAccessResponseDto accessDenied() {
    return new EpisodeAccessResponseDto(false, null);
  }

  public static EpisodeAccessResponseDto accessGranted(final String videoLink) {
    return new EpisodeAccessResponseDto(true, videoLink);
  }

}
