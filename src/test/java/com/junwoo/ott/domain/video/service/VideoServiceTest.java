package com.junwoo.ott.domain.video.service;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;

import com.junwoo.ott.domain.video.dto.body.VideoUpdateDto;
import com.junwoo.ott.domain.video.dto.request.VideoReadRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoUpdateRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoReadResponseDto;
import com.junwoo.ott.domain.video.dto.response.VideoUpdateResponseDto;
import com.junwoo.ott.domain.video.test.VideoTestValues;
import com.junwoo.ott.domain.video.dto.response.VideoCreateResponseDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.repository.VideoJpaRepository;
import com.junwoo.ott.global.common.entity.Timestamped;

import com.junwoo.ott.global.customenum.RatingType;
import jakarta.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
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
class VideoServiceTest implements VideoTestValues {

  @Mock
  private VideoJpaRepository videoJpaRepository;

  @InjectMocks
  private VideoService videoService;

  @Nested
  @DisplayName("비디오 생성 성공")
  class CreateVideo {

    @Test
    @DisplayName("생성 성공")
    void 비디오생성성공() {
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

  @Test
  @DisplayName("전체 비디오 조회")
  void 전체조회성공() {
    // given
    Pageable pageable = PageRequest.of(0, 10);
    Video baseVideo = Video.builder()
        .videoId(TEST_VIDEO_ID)
        .title(TEST_TITLE)
        .description(TEST_DESCRIPTION)
        .ratingType(TEST_RATING_TYPE)
        .build();

    // 타임스탬프 세팅
    setTimestamps(baseVideo, TEST_VIDEO_CREATED_AT, TEST_VIDEO_UPDATED_AT);

    List<Video> videoList = Arrays.asList(baseVideo);
    Page<Video> videoPage = new PageImpl<>(videoList, pageable, videoList.size());

    given(videoJpaRepository.getVideos(pageable)).willReturn(videoPage);

    // when
    Page<VideoReadResponseDto> result = videoService.getVideos(new VideoReadRequestDto(pageable));

    // then
    assertNotNull(result);
    assertEquals(1, result.getTotalElements());
    VideoReadResponseDto firstResult = result.getContent().get(0);
    assertEquals(TEST_TITLE, firstResult.getTitle());
    assertEquals(TEST_DESCRIPTION, firstResult.getDescription());
    assertEquals(TEST_RATING_TYPE, firstResult.getRatingType());
    assertEquals(TEST_VIDEO_CREATED_AT, firstResult.getCreatedAt());
  }

  @Test
  @DisplayName("단일 비디오 조회")
  void 단일조회성공() {
    // given
    Long videoId = 1L;
    Video baseVideo = Video.of(TEST_VIDEO_CREATE_REQUEST_DTO);
    setTimestamps(baseVideo, TEST_VIDEO_CREATED_AT, TEST_VIDEO_UPDATED_AT);
    given(videoJpaRepository.findById(videoId)).willReturn(Optional.of(baseVideo));

    // when
    VideoReadResponseDto result = videoService.getVideo(videoId);

    // then
    assertNotNull(result);
    assertEquals(TEST_TITLE, result.getTitle());
    assertEquals(TEST_DESCRIPTION, result.getDescription());
    assertEquals(TEST_VIDEO_CREATED_AT, result.getCreatedAt());
  }

  @Test
  @DisplayName("제목으로 비디오 조회")
  void 제목조회성공() {
    // given
    Pageable pageable = PageRequest.of(0, 10);
    Video baseVideo = Video.of(TEST_VIDEO_CREATE_REQUEST_DTO);
    setTimestamps(baseVideo, TEST_VIDEO_CREATED_AT, TEST_VIDEO_UPDATED_AT);
    List<Video> videos = List.of(baseVideo);
    Page<Video> videoPage = new PageImpl<>(videos, pageable, videos.size());
    given(videoJpaRepository.findByTitle(TEST_TITLE, pageable)).willReturn(videoPage);

    // when
    Page<VideoReadResponseDto> result = videoService.getVideosByTitle(
        new VideoReadRequestDto(TEST_TITLE, pageable));

    // then
    assertNotNull(result);
    assertEquals(1, result.getContent().size());
    assertEquals(TEST_TITLE, result.getContent().get(0).getTitle());
    assertEquals(TEST_VIDEO_CREATED_AT, result.getContent().get(0).getCreatedAt());
  }

  @Nested
  @DisplayName("비디오 수정")
  class UpdateVideo {

    @Test
    @DisplayName("비디오 수정 성공")
    void 비디오수정성공() {
      // given
      Video originalVideo = Video.builder()
          .videoId(TEST_VIDEO_ID)
          .title(TEST_TITLE)
          .description(TEST_DESCRIPTION)
          .ratingType(TEST_RATING_TYPE)
          .build();
      setTimestamps(originalVideo, TEST_VIDEO_CREATED_AT, TEST_VIDEO_UPDATED_AT);

      VideoUpdateDto updateDto = new VideoUpdateDto("수정된 제목", "수정된 내용", RatingType.RATE7);
      VideoUpdateRequestDto updateRequestDto = new VideoUpdateRequestDto(TEST_VIDEO_ID, updateDto);

      Video updatedVideo = originalVideo.toBuilder()
          .title(updateDto.getTitle())
          .description(updateDto.getDescription())
          .ratingType(updateDto.getRatingType())
          .build();
      given(videoJpaRepository.save(any(Video.class))).willReturn(updatedVideo);
      given(videoJpaRepository.findById(TEST_VIDEO_ID)).willReturn(Optional.of(originalVideo));

      // when
      VideoUpdateResponseDto result = videoService.updateVideo(updateRequestDto);

      // then
      assertEquals("수정된 제목", result.getVideo().getTitle());
      assertEquals("수정된 내용", result.getVideo().getDescription());
    }

    @Test
    @DisplayName("비디오id 없을시 예외 처리")
    void 비디오수정실패() {
      // given
      given(videoJpaRepository.findById(TEST_VIDEO_ID)).willReturn(Optional.empty());
      VideoUpdateDto dto = new VideoUpdateDto("수정된 제목", "수정된 내용", RatingType.RATE12);
      VideoUpdateRequestDto updateRequestDto = new VideoUpdateRequestDto(TEST_VIDEO_ID, dto);

      // when & then
      assertThrows(EntityNotFoundException.class, () -> videoService.updateVideo(updateRequestDto));
    }

    @Test
    @DisplayName("null일 경우 수정 실패")
    void 비디오null값처리() {
      // given
      Video originalVideo = Video.builder()
          .videoId(TEST_VIDEO_ID)
          .title(TEST_TITLE)
          .description(TEST_DESCRIPTION)
          .ratingType(TEST_RATING_TYPE)
          .build();
      setTimestamps(originalVideo, TEST_VIDEO_CREATED_AT, TEST_VIDEO_UPDATED_AT);
      given(videoJpaRepository.findById(TEST_VIDEO_ID)).willReturn(Optional.of(originalVideo));

      VideoUpdateDto dto = new VideoUpdateDto(null, "수정된 내용", RatingType.RATE15);// 제목이 null인 경우 테스트
      VideoUpdateRequestDto updateRequestDto = new VideoUpdateRequestDto(TEST_VIDEO_ID, dto);

      // when
      VideoUpdateResponseDto result = videoService.updateVideo(updateRequestDto);

      // then
      assertNotNull(result.getVideo().getTitle());
      assertEquals(TEST_TITLE, result.getVideo().getTitle());
      assertEquals("수정된 내용", result.getVideo().getDescription());
    }
  }

  public void setTimestamps(Video video, LocalDateTime createdAt, LocalDateTime updatedAt) {

    try {
      Field createdAtField = Timestamped.class.getDeclaredField("createdAt");
      createdAtField.setAccessible(true);
      createdAtField.set(video, createdAt);

      Field updatedAtField = Timestamped.class.getDeclaredField("updatedAt");
      updatedAtField.setAccessible(true);
      updatedAtField.set(video, updatedAt);
    } catch (NoSuchFieldException | IllegalAccessException e) {
      throw new RuntimeException("시간 설정에 실패했습니다.", e);
    }

  }

}
