package com.junwoo.ott.domain.episode.service;

import static com.junwoo.ott.domain.video.test.VideoTestValues.TEST_VIDEO_ID;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

import com.junwoo.ott.domain.episode.dto.body.EpisodeUpdateDto;
import com.junwoo.ott.domain.episode.dto.request.EpisodeReadRequestDto;
import com.junwoo.ott.domain.episode.dto.request.EpisodeUpdateRequestDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeCreateResponseDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeReadResponseDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeUpdateResponseDto;
import com.junwoo.ott.domain.episode.entity.Episode;
import com.junwoo.ott.domain.episode.repository.EpisodeRepository;

import com.junwoo.ott.domain.episode.test.EpisodeTestValues;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.service.VideoService;
import com.junwoo.ott.global.customenum.MembershipType;
import jakarta.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
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
                .membershipType(TEST_MEMBERSHIP_TYPE)
                .videoLink(TEST_VIDEO_LINK)
                .video(video)
                .build();

            given(videoService.getByVideoId(TEST_VIDEO_ID)).willReturn(video);
            given(episodeRepository.save(any(Episode.class))).willReturn(episode);
            // when
            EpisodeCreateResponseDto dto = episodeService.createEpisode(TEST_EPISODE_CREATE_REQUEST_DTO);
            // then
            assertEquals(TEST_EPISODE_TITLE, dto.getTitle());
            assertEquals(TEST_RELEASED_AT, dto.getReleasedAt());
            assertEquals(TEST_VIDEO_LINK, dto.getVideoLink());
            assertEquals(TEST_MEMBERSHIP_TYPE, dto.getMembershipType());
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
                () -> episodeService.createEpisode(TEST_EPISODE_CREATE_REQUEST_DTO), "비디오 id를 찾을 수 없습니다.");
        }

    }

    @Nested
    @DisplayName("에피소드 조회")
    class GetEpisode {

        @Test
        @DisplayName("전체 에피소드 조회 성공")
        void 전체조회성공() {
            // given
            Pageable pageable = PageRequest.of(TEST_PAGE, TEST_SIZE);
            EpisodeReadRequestDto requestDto = new EpisodeReadRequestDto(TEST_VIDEO_ID, pageable, TEST_MEMBERSHIP_TYPE);

            Video video = Video.builder()
                .videoId(TEST_VIDEO_ID)
                .build();

            Episode episode = Episode.builder()
                .episodeId(TEST_EPISODE_ID)
                .title(TEST_EPISODE_TITLE)
                .releasedAt(TEST_RELEASED_AT)
                .membershipType(TEST_MEMBERSHIP_TYPE)
                .videoLink(TEST_VIDEO_LINK)
                .video(video)
                .build();

            List<Episode> episodes = List.of(episode);
            Page<Episode> episodePage = new PageImpl<>(episodes, pageable, episodes.size());


            given(episodeRepository.findByVideoIdAndMembershipType(TEST_VIDEO_ID, TEST_MEMBERSHIP_TYPE, pageable)).willReturn(episodePage);

            // when
            Page<EpisodeReadResponseDto> result = episodeService.getEpisodesByVideo(requestDto);

            // then
            assertNotNull(result);
            assertEquals(1, result.getTotalElements());
            EpisodeReadResponseDto firstEpisodeDto = result.getContent().get(0);
            assertEquals(TEST_EPISODE_TITLE, firstEpisodeDto.getTitle());
            assertEquals(TEST_RELEASED_AT, firstEpisodeDto.getReleasedAt());
            assertEquals(TEST_MEMBERSHIP_TYPE, firstEpisodeDto.getMembershipType());
            assertEquals(TEST_VIDEO_LINK, firstEpisodeDto.getVideoLink());
        }

        @Test
        @DisplayName("단건 에피소드 조회 성공")
        void 단건조회성공() {
            // given
            EpisodeReadRequestDto fetchRequestDto = new EpisodeReadRequestDto(TEST_VIDEO_ID, TEST_EPISODE_ID, TEST_MEMBERSHIP_TYPE);

            Video video = Video.builder()
                .videoId(TEST_VIDEO_ID)
                .build();
            Episode episode = Episode.builder()
                .episodeId(TEST_EPISODE_ID)
                .title(TEST_EPISODE_TITLE)
                .releasedAt(TEST_RELEASED_AT)
                .membershipType(TEST_MEMBERSHIP_TYPE)
                .videoLink(TEST_VIDEO_LINK)
                .video(video)
                .build();

            given(episodeRepository.findByVideoIdAndEpisodeIdAndMembershipType(TEST_VIDEO_ID, TEST_EPISODE_ID, TEST_MEMBERSHIP_TYPE))
                .willReturn(Optional.of(episode));

            // when
            EpisodeReadResponseDto result = episodeService.getEpisodeByVideo(fetchRequestDto);

            // then
            assertNotNull(result);
            assertEquals(TEST_EPISODE_TITLE, result.getTitle());
            assertEquals(TEST_RELEASED_AT, result.getReleasedAt());
            assertEquals(TEST_MEMBERSHIP_TYPE, result.getMembershipType());
            assertEquals(TEST_VIDEO_LINK, result.getVideoLink());
        }


    }

    @Nested
    @DisplayName("에피소드 수정 성공")
    class UpdateEpisode {

        @Test
        @DisplayName("수정 성공")
        void 수정성공() {
            // given
            EpisodeUpdateDto updateDto = new EpisodeUpdateDto(
                "수정된 제목",
                LocalDateTime.now(),
                TEST_MEMBERSHIP_TYPE,
                TEST_VIDEO_LINK
            );
            EpisodeUpdateRequestDto updateRequestDto = new EpisodeUpdateRequestDto(TEST_VIDEO_ID, TEST_EPISODE_ID, updateDto);

            Video video = Video.builder()
                .videoId(TEST_VIDEO_ID)
                .build();
            Episode existingEpisode = Episode.builder()
                .episodeId(TEST_EPISODE_ID)
                .title(TEST_EPISODE_TITLE)
                .releasedAt(TEST_RELEASED_AT)
                .membershipType(TEST_MEMBERSHIP_TYPE)
                .videoLink(TEST_VIDEO_LINK)
                .video(video)
                .build();

            given(episodeRepository.findById(TEST_EPISODE_ID)).willReturn(Optional.of(existingEpisode));
            given(episodeRepository.save(any(Episode.class))).willReturn(existingEpisode);

            // when
            EpisodeUpdateResponseDto responseDto = episodeService.updateEpisode(updateRequestDto);

            // then
            verify(episodeRepository).findById(TEST_EPISODE_ID);
            verify(episodeRepository).save(existingEpisode);
            assertNotNull(responseDto);
        }

    }

    @Nested
    @DisplayName("에피소드 수정 실패")
    class UpdateEpisodeFailure {

        @Test
        @DisplayName("수정 실패")
        void 수정실패() {
            // given
            Long invalidEpisodeId = TEST_EPISODE_ID;
            EpisodeUpdateDto updateDto = new EpisodeUpdateDto(
                "수정된 에피소드 제목",
                LocalDateTime.now(),
                TEST_MEMBERSHIP_TYPE,
                TEST_VIDEO_LINK
            );
            EpisodeUpdateRequestDto updateRequestDto = new EpisodeUpdateRequestDto(TEST_VIDEO_ID, invalidEpisodeId, updateDto);

            given(episodeRepository.findById(invalidEpisodeId)).willReturn(Optional.empty());

            // when & then
            assertThrows(EntityNotFoundException.class, () -> {
                episodeService.updateEpisode(updateRequestDto);
            }, "에피소드 id를 찾을 수 없습니다.");
        }

    }

    @Nested
    @DisplayName("에피소드 삭제")
    class DeleteEpisode {

        @Test
        @DisplayName("에피소드 삭제 성공")
        void 삭제성공() {
            // given
            given(episodeRepository.existsById(TEST_EPISODE_ID)).willReturn(true);

            // when
            assertDoesNotThrow(() -> episodeService.deleteEpisode(TEST_EPISODE_ID));

            // then
            verify(episodeRepository).softDeleteEpisodeById(TEST_EPISODE_ID);
        }

        @Test
        @DisplayName("에피소드 삭제 실패")
        void 삭제실패() {
            // given
            Long nonExistentEpisodeId = 2L;
            given(episodeRepository.existsById(nonExistentEpisodeId)).willReturn(false);

            // when & then
            assertThrows(EntityNotFoundException.class,
                () -> episodeService.deleteEpisode(nonExistentEpisodeId));
            verify(episodeRepository, never()).softDeleteEpisodeById(nonExistentEpisodeId);
        }

    }

}
