package com.junwoo.ott.domain.episode.test;

import com.junwoo.ott.domain.episode.dto.request.EpisodeCreateRequestDto;
import java.time.LocalDateTime;

public interface EpisodeTestValues {
    Long TEST_EPISODE_ID = 1L;
    String TEST_EPISODE_TITLE = "테스트용 제목";
    LocalDateTime TEST_RELEASED_AT = LocalDateTime.of(2024, 3, 15, 12, 0);
    LocalDateTime TEST_EPISODE_CREATED_AT = LocalDateTime.now();
    LocalDateTime TEST_EPISODE_UPDATED_AT = LocalDateTime.now();
    
    EpisodeCreateRequestDto TEST_EPISODE_CREATE_REQUEST_DTO = new EpisodeCreateRequestDto(
        TEST_EPISODE_TITLE,
        TEST_RELEASED_AT
    );
}
