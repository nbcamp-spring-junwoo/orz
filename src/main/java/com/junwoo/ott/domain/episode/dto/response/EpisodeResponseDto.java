package com.junwoo.ott.domain.episode.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeResponseDto {

  private String episodeId;
  private String title;
  private String description;
  private LocalDate releasedAt;

}
