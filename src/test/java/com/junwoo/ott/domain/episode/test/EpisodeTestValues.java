package com.junwoo.ott.domain.episode.test;

import com.junwoo.ott.domain.episode.dto.request.EpisodeCreateRequestDto;
import com.junwoo.ott.global.customenum.MembershipType;
import java.time.LocalDateTime;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface EpisodeTestValues {
    Long TEST_EPISODE_ID = 1L;
    String TEST_EPISODE_TITLE = "테스트용 제목";
    LocalDateTime TEST_RELEASED_AT = LocalDateTime.of(2024, 3, 15, 12, 0);
    MembershipType TEST_MEMBERSHIP_TYPE = MembershipType.ROLE_BRONZE;
    String TEST_VIDEO_LINK = "http://example.com/test-video";
    Integer TEST_PAGE = 0;
    Integer TEST_SIZE = 10;
    
    EpisodeCreateRequestDto TEST_EPISODE_CREATE_REQUEST_DTO = new EpisodeCreateRequestDto(
        TEST_EPISODE_ID,
        TEST_EPISODE_TITLE,
        TEST_RELEASED_AT,
        TEST_MEMBERSHIP_TYPE,
        TEST_VIDEO_LINK
    );
}
