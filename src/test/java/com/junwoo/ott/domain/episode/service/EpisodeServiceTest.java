package com.junwoo.ott.domain.episode.service;

import static com.junwoo.ott.domain.video.test.VideoTestValues.TEST_VIDEO_ID;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import com.junwoo.ott.domain.episode.dto.response.EpisodeCreateResponseDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeReadResponseDto;
import com.junwoo.ott.domain.episode.entity.Episode;
import com.junwoo.ott.domain.episode.repository.EpisodeRepository;

import com.junwoo.ott.domain.episode.test.EpisodeTestValues;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.service.VideoService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

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

    @Nested
    @DisplayName("에피소드 조회")
    class GetEpisode {

        @Test
        @DisplayName("전체 에피소드 조회 성공")
        void 전체에피소드조회성공() {
            // given
            Pageable pageable = PageRequest.of(0, 10);
            Episode episode = Episode.builder()
                .episodeId(TEST_EPISODE_ID)
                .title(TEST_EPISODE_TITLE)
                .releasedAt(TEST_RELEASED_AT)
                .build();
            List<Episode> episodes = Collections.singletonList(episode);
            Page<Episode> episodePage = new PageImpl<>(episodes, pageable, episodes.size());

            given(episodeRepository.findByEpisodeId(TEST_VIDEO_ID, pageable)).willReturn(episodePage);

            // when
            Page<EpisodeReadResponseDto> result = episodeService.getEpisodesByVideo(TEST_VIDEO_ID, pageable);

            // then
            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            EpisodeReadResponseDto firstResult = result.getContent().get(0);
            assertEquals(TEST_EPISODE_TITLE, firstResult.getTitle());
            assertEquals(TEST_RELEASED_AT, firstResult.getReleasedAt());
        }

        @Test
        @DisplayName("단건 에피소드 조회 성공")
        void 단건에피소드조회성공() {
            // given
            Episode episode = Episode.builder()
                .episodeId(TEST_EPISODE_ID)
                .title(TEST_EPISODE_TITLE)
                .releasedAt(TEST_RELEASED_AT)
                .build();

            given(episodeRepository.findByVideoIdAndEpisodeId(TEST_VIDEO_ID, TEST_EPISODE_ID)).willReturn(Optional.of(episode));

            // when
            EpisodeReadResponseDto result = episodeService.getEpisodeByVideo(TEST_VIDEO_ID, TEST_EPISODE_ID);

            // then
            assertNotNull(result);
            assertEquals(TEST_EPISODE_TITLE, result.getTitle());
            assertEquals(TEST_RELEASED_AT, result.getReleasedAt());
        }

    }

}
