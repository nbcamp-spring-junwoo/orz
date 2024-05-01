package com.junwoo.ott.domain.genre.service;

import com.junwoo.ott.domain.genre.dto.response.GenreResponseDto;
import com.junwoo.ott.domain.genre.repository.GenreRepository;
import com.junwoo.ott.global.customenum.GenreType;
import jakarta.transaction.Transactional;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class GenreService {

  private final GenreRepository genreRepository;

  public List<GenreResponseDto> getAllIn(final Set<GenreType> genreTypes) {
    return genreRepository.findAllByGenreTypeIn(genreTypes).stream()
        .map(GenreResponseDto::new).toList();
  }

}
