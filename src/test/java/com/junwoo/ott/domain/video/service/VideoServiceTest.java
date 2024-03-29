
package com.junwoo.ott.domain.video.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoCreateResponseDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.repository.VideoJpaRepository;
import com.junwoo.ott.global.customenum.RatingType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest {

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
      VideoCreateRequestDto createRequestDto = new VideoCreateRequestDto("", "테스트설명1",
          RatingType.EVERYONE);
      Video video = Video.builder()
          .title(createRequestDto.getTitle())
          .description(createRequestDto.getDescription())
          .ratingType(createRequestDto.getRatingType())
          .build();

      given(videoJpaRepository.save(any(Video.class))).willReturn(video);

      // when
      VideoCreateResponseDto result = videoService.createVideo(createRequestDto);

      // then
      assertEquals(result.getTitle(), createRequestDto.getTitle());
      assertEquals(result.getDescription(), createRequestDto.getDescription());
      assertEquals(result.getRatingType(), createRequestDto.getRatingType());

    }

    @Nested
    @DisplayName("비디오 생성 실패")
    class CreateVideoFailure {

      @Test
      @DisplayName("저장 시 예외 발생")
      void 비디오생성실패() {
        // given
        VideoCreateRequestDto createRequestDto = new VideoCreateRequestDto("테스트제목1", "테스트설명1",
            RatingType.RATE7);
        given(videoJpaRepository.save(any(Video.class))).willThrow(new RuntimeException("생성 실패"));

        // when & then
        assertThrows(RuntimeException.class, () -> videoService.createVideo(createRequestDto),
            "생성 실패");
      }
    }

  }

}
