package com.junwoo.ott.domain.episode.service;

import static com.junwoo.ott.domain.video.test.VideoTestValues.TEST_VIDEO_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.junwoo.ott.domain.episode.dto.response.EpisodeCreateResponseDto;
import com.junwoo.ott.domain.episode.entity.Episode;
import com.junwoo.ott.domain.episode.repository.EpisodeRepository;

import com.junwoo.ott.domain.episode.test.EpisodeTestValues;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.service.VideoService;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EpisodeServiceTest implements EpisodeTestValues {

    @Mock
    private EpisodeRepository episodeRepository;

    @Mock
    private VideoService videoService;

    @InjectMocks
    private EpisodeService episodeService;

    @Nested
    @DisplayName("에피소드 생성 성공")
    class CreateEpisode {

        @Test
        @DisplayName("생성 성공")
        void 에피소드생성성공() {
            // given
            Video video = Video.builder()
                .videoId(TEST_VIDEO_ID)
                .build();

            Episode episode = Episode.builder()
                .title(TEST_EPISODE_TITLE)
                .releasedAt(TEST_RELEASED_AT)
                .video(video)
                .build();

            given(videoService.getByVideoId(TEST_VIDEO_ID)).willReturn(video);
            given(episodeRepository.save(any(Episode.class))).willReturn(episode);

            // when
            EpisodeCreateResponseDto dto = episodeService.createEpisode(TEST_VIDEO_ID, TEST_EPISODE_CREATE_REQUEST_DTO);

            // then
            assertEquals(TEST_EPISODE_TITLE, dto.getTitle());
            assertEquals(TEST_RELEASED_AT, dto.getReleasedAt());
        }

    }

    @Nested
    @DisplayName("에피소드 생성 실패")
    class CreateEpisodeFailure {

        @Test
        @DisplayName("생성 실패")
        void 에피소드생성실패() {
            // given
            given(videoService.getByVideoId(TEST_VIDEO_ID)).willThrow(new EntityNotFoundException("비디오 id를 찾을 수 없습니다."));

            // when & then
            assertThrows(EntityNotFoundException.class,
                () -> episodeService.createEpisode(TEST_VIDEO_ID, TEST_EPISODE_CREATE_REQUEST_DTO), "비디오 id를 찾을 수 없습니다.");
        }

    }

}
