package com.junwoo.ott.domain.video.dto.request;

import com.junwoo.ott.global.customenum.GenreType;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
public class VideoRequestDto {

  private final Pageable pageable;
  private final List<GenreType> genreTypeList;
  private final Boolean isIntersection;

  public VideoRequestDto(
      final Pageable pageable,
      final List<GenreType> genreTypeList,
      final Boolean isIntersection
  ) {
    this.pageable = pageable;
    this.genreTypeList = genreTypeList;
    this.isIntersection = isIntersection;
  }

}
