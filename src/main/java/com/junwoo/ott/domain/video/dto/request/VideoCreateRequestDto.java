package com.junwoo.ott.domain.video.dto.request;

import com.junwoo.ott.domain.video.dto.body.VideoCreateDto;
import com.junwoo.ott.global.customenum.GenreType;
import com.junwoo.ott.global.customenum.MembershipType;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
import lombok.Getter;

@Getter
public class VideoCreateRequestDto {

  private final String originalTitle;
  private final String title;
  private final String description;
  private final MembershipType membershipType;
  private final String posterUrl;
  private final Set<GenreType> genreTypeSet;
  private final LocalDate releasedAt;

  public VideoCreateRequestDto(final VideoCreateDto videoCreateDto) {
    this.originalTitle = videoCreateDto.getOriginalTitle();
    this.title = videoCreateDto.getTitle();
    this.description = videoCreateDto.getDescription();
    this.membershipType = videoCreateDto.getMembershipType();
    this.posterUrl = videoCreateDto.getPosterUrl();
    this.genreTypeSet = new HashSet<>(videoCreateDto.getGenreTypeList());
    this.releasedAt = LocalDate.parse(videoCreateDto.getReleasedAt());
  }

}
