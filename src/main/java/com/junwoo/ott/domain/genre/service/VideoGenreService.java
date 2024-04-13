package com.junwoo.ott.domain.genre.service;

import com.junwoo.ott.domain.genre.entity.VideoGenre;
import com.junwoo.ott.domain.genre.repository.VideoGenreRepository;
import com.junwoo.ott.global.customenum.GenreType;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class VideoGenreService {

  private final VideoGenreRepository videoGenreRepository;

  private final GenreService genreService;

  public void createVideoGenre(final Long videoId, final Set<GenreType> genreTypeSet) {
    List<VideoGenre> videoGenreList = genreService.getAllIn(genreTypeSet).stream()
        .map(genreResponseDto -> new VideoGenre(videoId, genreResponseDto.getGenreId())).toList();

    videoGenreRepository.saveAll(videoGenreList);
  }

}
