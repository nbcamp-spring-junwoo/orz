package com.junwoo.ott.domain.genre.dto.response;

import com.junwoo.ott.domain.genre.entity.Genre;
import com.junwoo.ott.global.customenum.GenreType;
import lombok.Getter;

@Getter
public class GenreResponseDto {

  private final Long GenreId;
  private final GenreType genreType;

  public GenreResponseDto(final Genre genre) {
    this.GenreId = genre.getGenreId();
    this.genreType = genre.getGenreType();
  }

}
