package com.junwoo.ott.domain.video.dto.request;

import com.junwoo.ott.domain.video.dto.body.VideoCreateDto;
import com.junwoo.ott.global.customenum.GenreType;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class VideoCreateRequestDto {

  private final String title;
  private final String description;
  private final String posterUrl;
  private final Set<GenreType> genreTypeSet;
  private final LocalDate releasedAt;

  public VideoCreateRequestDto(final VideoCreateDto videoCreateDto) {
    this.title = videoCreateDto.getTitle();
    this.description = videoCreateDto.getDescription();
    this.posterUrl = videoCreateDto.getPosterUrl();
    this.genreTypeSet = new HashSet<>(videoCreateDto.getGenreTypeList());
    this.releasedAt = LocalDate.parse(videoCreateDto.getReleasedAt());
  }

}
