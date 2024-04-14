package com.junwoo.ott.domain.video.dto.response;

import com.junwoo.ott.domain.episode.dto.response.EpisodeProjectionDto;
import com.junwoo.ott.global.customenum.GenreType;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoReadResponseDto {

  private Long videoId;
  private String title;
  private String description;
  private String posterUrl;
  private List<GenreType> genreTypeList;
  private Page<EpisodeProjectionDto> episodeResponseDtoPage;

  public VideoReadResponseDto(
      final VideoProjectionDto videoProjectionDto,
      final List<GenreType> genreTypeList,
      final Page<EpisodeProjectionDto> episodeResponseDtoPage
  ) {
    this.videoId = videoProjectionDto.getVideoId();
    this.title = videoProjectionDto.getTitle();
    this.description = videoProjectionDto.getDescription();
    this.posterUrl = videoProjectionDto.getPosterUrl();
    this.genreTypeList = genreTypeList;
    this.episodeResponseDtoPage = episodeResponseDtoPage;
  }
}
