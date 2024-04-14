package com.junwoo.ott.domain.video.repository;

import com.junwoo.ott.domain.video.dto.response.VideoReadResponseDto;
import org.springframework.data.domain.Pageable;

public interface VideoQueryDslRepository {

  VideoReadResponseDto findOneQuery(final Long videoId, final Pageable pageable);

}
