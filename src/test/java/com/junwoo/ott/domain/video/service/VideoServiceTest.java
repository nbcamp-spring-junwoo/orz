
package com.junwoo.ott.domain.video.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.junwoo.ott.domain.video.common.VideoTestCommons;
import com.junwoo.ott.domain.video.dto.response.VideoCreateResponseDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.repository.VideoJpaRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest implements VideoTestCommons {

  @Mock
  private VideoJpaRepository videoJpaRepository;

  @InjectMocks
  private VideoService videoService;

  @Nested
  @DisplayName("비디오 생성 성공")
  class CreateVideo {

    @Test
    @DisplayName("생성 성공")
    void 비디오생성() {
      // given
      Video video = Video.builder()
          .title(TEST_VIDEO_CREATE_REQUEST_DTO.getTitle())
          .description(TEST_VIDEO_CREATE_REQUEST_DTO.getDescription())
          .ratingType(TEST_VIDEO_CREATE_REQUEST_DTO.getRatingType())
          .build();

      given(videoJpaRepository.save(any())).willReturn(video);

      // when
      VideoCreateResponseDto dto = videoService.createVideo(TEST_VIDEO_CREATE_REQUEST_DTO);

      // then
      assertEquals(TEST_TITLE, dto.getTitle());
      assertEquals(TEST_DESCRIPTION, dto.getDescription());
      assertEquals(TEST_RATING_TYPE, dto.getRatingType());
    }
  }

  @Nested
  @DisplayName("비디오 생성 실패")
  class CreateVideoFailure {

    @Test
    @DisplayName("생성 실패")
    void 비디오생성실패() {
      // given
      given(videoJpaRepository.save(any())).willThrow(new RuntimeException("생성 실패"));

      // when & then
      assertThrows(RuntimeException.class,
          () -> videoService.createVideo(TEST_VIDEO_CREATE_REQUEST_DTO), "생성 실패");
    }
  }

}
