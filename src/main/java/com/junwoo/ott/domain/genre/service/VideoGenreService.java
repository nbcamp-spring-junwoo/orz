package com.junwoo.ott.domain.genre.service;

import com.junwoo.ott.domain.genre.dto.response.GenreResponseDto;
import com.junwoo.ott.domain.genre.entity.VideoGenre;
import com.junwoo.ott.domain.genre.repository.VideoGenreRepository;
import com.junwoo.ott.global.customenum.GenreType;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class VideoGenreService {

  private final VideoGenreRepository videoGenreRepository;

  private final GenreService genreService;

  public void createVideoGenre(final Long videoId, final Set<GenreType> genreTypeSet) {
    Set<Long> genreIdList = genreService.getAllIn(genreTypeSet).stream()
        .map(GenreResponseDto::getGenreId)
        .collect(Collectors.toSet());

    Set<Long> alreadyGenreIdSet = videoGenreRepository.findAllByVideoId(videoId).stream()
        .map(VideoGenre::getGenreId)
        .collect(Collectors.toSet());

    genreIdList.removeAll(alreadyGenreIdSet);

    List<VideoGenre> videoGenreList = genreIdList.stream().map(
        genreId -> new VideoGenre(videoId, genreId)
    ).toList();

    videoGenreRepository.saveAll(videoGenreList);
  }

}
