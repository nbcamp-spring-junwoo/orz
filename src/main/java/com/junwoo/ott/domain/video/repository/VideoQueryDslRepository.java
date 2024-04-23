package com.junwoo.ott.domain.video.repository;

import com.junwoo.ott.domain.video.dto.response.VideoReadResponseDto;
import com.junwoo.ott.global.customenum.GenreType;
import com.querydsl.core.Tuple;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoQueryDslRepository {

  VideoReadResponseDto findOneQuery(final Long videoId, final Pageable pageable);

  Page<Tuple> findAllByFilter(
      final Pageable pageable, final List<GenreType> genreTypeList, final Boolean isIntersection
  );

}
