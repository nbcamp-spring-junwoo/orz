package com.junwoo.ott.domain.episode.dto.body;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class EpisodeCreateDto {

  @NotNull
  private Long videoId;
  @NotNull
  private String title;
  @NotNull
  private String description;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private String releasedAt;

}
