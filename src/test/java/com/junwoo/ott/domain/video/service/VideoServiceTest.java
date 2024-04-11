package com.junwoo.ott.domain.video.service;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.junwoo.ott.domain.category.dto.body.CategoryInfoDto;
import com.junwoo.ott.domain.category.entity.Category;
import com.junwoo.ott.domain.category.entity.VideoCategory;
import com.junwoo.ott.domain.category.service.CategoryService;
import com.junwoo.ott.domain.category.service.VideoCategoryService;
import com.junwoo.ott.domain.video.dto.body.VideoSearchByCategoryDto;
import com.junwoo.ott.domain.video.dto.body.VideoUpdateDto;
import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoReadRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoUpdateRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoReadResponseDto;
import com.junwoo.ott.domain.video.dto.response.VideoUpdateResponseDto;
import com.junwoo.ott.domain.video.test.VideoTestValues;
import com.junwoo.ott.domain.video.dto.response.VideoCreateResponseDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.repository.VideoJpaRepository;
import com.junwoo.ott.global.common.entity.Timestamped;

import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import com.junwoo.ott.global.customenum.RatingType;
import com.junwoo.ott.global.exception.custom.TitleNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class VideoServiceTest implements VideoTestValues {

  @Mock
  private VideoJpaRepository videoJpaRepository;
  @Mock
  private CategoryService categoryService;
  @Mock
  private VideoCategoryService videoCategoryService;

  @InjectMocks
  private VideoService videoService;

  @Nested
  @DisplayName("비디오 생성 성공")
  class CreateVideo {

    @Test
    @DisplayName("생성 성공")
    void 비디오생성성공() {
      // given
      VideoCreateRequestDto requestDto = new VideoCreateRequestDto(TEST_TITLE, TEST_DESCRIPTION, TEST_RATING_TYPE, TEST_CATEGORY_IDS);

      Video savedVideo = Video.builder()
          .videoId(TEST_VIDEO_ID)
          .title(TEST_TITLE)
          .description(TEST_DESCRIPTION)
          .ratingType(TEST_RATING_TYPE)
          .build();

      Category category1 = Category.builder().categoryId(1L).type(CategoryType.ANIME).build();
      Category category2 = Category.builder().categoryId(4L).type(CategoryType.DRAMA).build();
      when(categoryService.existsCategoryById(1L)).thenReturn(category1);
      when(categoryService.existsCategoryById(4L)).thenReturn(category2);
      when(videoJpaRepository.save(any(Video.class))).thenReturn(savedVideo);

      // when
      VideoCreateResponseDto result = videoService.createVideo(requestDto);

      // then
      assertNotNull(result);
      assertEquals(TEST_TITLE, result.getTitle());
      assertEquals(TEST_DESCRIPTION, result.getDescription());
      assertEquals(TEST_RATING_TYPE, result.getRatingType());
      assertTrue(result.getCategoryIds().containsAll(TEST_CATEGORY_IDS));

      verify(videoJpaRepository).save(any(Video.class));
      verify(categoryService).existsCategoryById(1L);
      verify(categoryService).existsCategoryById(4L);
      verify(videoCategoryService, times(TEST_CATEGORY_IDS.size())).associateVideoWithCategory(any(Video.class), any(Category.class));
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

  @Nested
  @DisplayName("비디오 조회")
  class getVideo {

    @Test
    @DisplayName("전체조회")
    void 비디오전체조회() {
      // given
      Page<Video> mockPage = new PageImpl<>(List.of(setupMockVideoWithCategories()));
      when(videoJpaRepository.getVideos(any(Pageable.class))).thenReturn(mockPage);

      // when
      Page<VideoReadResponseDto> result = videoService.getVideos(
          new VideoReadRequestDto(TEST_PAGEABLE));

      // then
      assertNotNull(result);
      assertEquals(1, result.getTotalElements());
      VideoReadResponseDto videoDto = result.getContent().get(0);
      assertNotNull(videoDto.getCategories());
      assertFalse(videoDto.getCategories().isEmpty());

      verify(videoJpaRepository).getVideos(TEST_PAGEABLE);
    }

    @Test
    @DisplayName("단건조회")
    void 비디오단건조회() {
      // given
      Video video = setupMockVideoWithCategories();
      when(videoJpaRepository.findById(anyLong())).thenReturn(Optional.of(video));

      // when
      VideoReadResponseDto result = videoService.getVideo(TEST_VIDEO_ID);

      // then
      assertNotNull(result);
      assertEquals(TEST_VIDEO_ID, result.getVideoId());

      verify(videoJpaRepository).findById(TEST_VIDEO_ID);
    }

    @Test
    @DisplayName("제목조회")
    void 비디오제목조회() {
      // given
      Page<Video> mockPage = new PageImpl<>(List.of(setupMockVideoWithCategories()));
      when(videoJpaRepository.findByTitle(eq(TEST_TITLE), any(Pageable.class))).thenReturn(
          mockPage);

      // when
      Page<VideoReadResponseDto> result = videoService.getVideosByTitle(
          new VideoReadRequestDto(TEST_TITLE, TEST_PAGEABLE));

      // then
      assertNotNull(result);
      assertEquals(1, result.getTotalElements());

      verify(videoJpaRepository).findByTitle(TEST_TITLE, TEST_PAGEABLE);
    }

    @Test
    @DisplayName("카테고리조회")
    void 비디오카테고리조회() {
      // given
      Video mockVideo = setupMockVideoWithCategories();
      Category mockCategory = Mockito.mock(Category.class);
      VideoCategory videoCategory = new VideoCategory(mockVideo, mockCategory);
      Page<VideoCategory> mockPage = new PageImpl<>(List.of(videoCategory));

      // when
      when(videoCategoryService.findVideosByCategory(any(CategoryType.class), anySet(),
          any(Pageable.class))).thenReturn(mockPage);

      // then
      VideoSearchByCategoryDto searchDto = new VideoSearchByCategoryDto(CategoryType.ANIME,
          Set.of(GenreType.ACTION));
      Page<VideoReadResponseDto> result = videoService.getVideosByCategory(searchDto,
          TEST_PAGEABLE);

      assertNotNull(result);
      assertEquals(1, result.getTotalElements());

      // Verify
      verify(videoCategoryService).findVideosByCategory(CategoryType.ANIME,
          Set.of(GenreType.ACTION), TEST_PAGEABLE);
    }

  }

  @Nested
  @DisplayName("비디오 수정")
  class UpdateVideo {

    @Test
    @DisplayName("비디오 수정 성공")
    void 비디오수정성공() {
      // Given
      Video originalVideo = Video.builder()
          .videoId(TEST_VIDEO_ID)
          .title(TEST_TITLE)
          .description(TEST_DESCRIPTION)
          .ratingType(TEST_RATING_TYPE)
          .build();

      VideoUpdateDto updateDto = new VideoUpdateDto("수정된 제목", "수정된 내용", RatingType.RATE7, TEST_CATEGORY_IDS);

      VideoUpdateRequestDto updateRequestDto = new VideoUpdateRequestDto(TEST_VIDEO_ID, updateDto);

      Video updatedVideo = Video.builder()
          .videoId(TEST_VIDEO_ID)
          .title(updateDto.getTitle())
          .description(updateDto.getDescription())
          .ratingType(updateDto.getRatingType())
          .build();

      given(videoJpaRepository.save(any(Video.class))).willReturn(updatedVideo);
      given(videoJpaRepository.findById(TEST_VIDEO_ID)).willReturn(Optional.of(originalVideo));

      // When
      VideoUpdateResponseDto result = videoService.updateVideo(updateRequestDto);

      // Then
      assertEquals("수정된 제목", result.getTitle());
      assertEquals("수정된 내용", result.getDescription());
    }

    @Test
    @DisplayName("비디오id 없을시 예외 처리")
    void 비디오수정실패() {
      // Given
      given(videoJpaRepository.findById(TEST_VIDEO_ID)).willReturn(Optional.empty());
      VideoUpdateDto dto = new VideoUpdateDto("수정된 제목", "수정된 내용", RatingType.RATE12, TEST_CATEGORY_IDS);
      VideoUpdateRequestDto updateRequestDto = new VideoUpdateRequestDto(TEST_VIDEO_ID, dto);

      // When & Then
      assertThrows(EntityNotFoundException.class, () -> videoService.updateVideo(updateRequestDto));
    }

    @Test
    @DisplayName("null일 경우 수정 실패")
    void 비디오null값처리() {
      // Given
      Video originalVideo = Video.builder()
          .videoId(TEST_VIDEO_ID)
          .title(TEST_TITLE)
          .description(TEST_DESCRIPTION)
          .ratingType(TEST_RATING_TYPE)
          .build();
      setTimestamps(originalVideo, TEST_VIDEO_CREATED_AT, TEST_VIDEO_UPDATED_AT);
      given(videoJpaRepository.findById(TEST_VIDEO_ID)).willReturn(Optional.of(originalVideo));

      VideoUpdateDto dto = new VideoUpdateDto(null, "수정된 내용", RatingType.RATE15, TEST_CATEGORY_IDS);
      VideoUpdateRequestDto updateRequestDto = new VideoUpdateRequestDto(TEST_VIDEO_ID, dto);

      // When
      VideoUpdateResponseDto result = videoService.updateVideo(updateRequestDto);

      // Then
      assertNotNull(result.getTitle());
      assertEquals(TEST_TITLE, result.getTitle());
      assertEquals("수정된 내용", result.getDescription());
    }

  }

  @Nested
  @DisplayName("제목으로 비디오 조회 실패")
  class GetVideosByTitleFailure {

    @Test
    @DisplayName("TitleNotFoundException 동작 확인")
    void 제목조회실패() {
      // Given
      VideoReadRequestDto requestDto = new VideoReadRequestDto("", TEST_PAGEABLE);

      // When
      TitleNotFoundException thrown = assertThrows(TitleNotFoundException.class,
          () -> videoService.getVideosByTitle(requestDto));

      // Then
      assertEquals("제목을 찾을 수 없습니다.", thrown.getMessage());
    }

  }

  @Nested
  @DisplayName("소프트 삭제 확인")
  class SoftDeletionProcess {

    @Test
    @DisplayName("비디오 삭제 성공")
    void 비디오삭제() {
      // Given
      given(videoJpaRepository.existsById(TEST_VIDEO_ID)).willReturn(true);
      doNothing().when(videoJpaRepository).softDeleteVideoById(TEST_VIDEO_ID);

      // When
      videoService.deleteVideo(TEST_VIDEO_ID);

      // Then
      verify(videoJpaRepository).softDeleteVideoById(TEST_VIDEO_ID);
    }

  }

  @Nested
  @DisplayName("ID 조회")
  class GetVideosByIds {

    @Test
    @DisplayName("ID목록으로 조회 성공")
    void 리스트조회() {
      // given
      List<Long> videoIds = List.of(TEST_VIDEO_ID);
      List<Video> expectedVideos = List.of(
          Video.builder()
              .videoId(TEST_VIDEO_ID)
              .title(TEST_TITLE)
              .description(TEST_DESCRIPTION)
              .ratingType(TEST_RATING_TYPE)
              .build()
      );

      given(videoJpaRepository.findAllById(videoIds)).willReturn(expectedVideos);

      // when
      List<Video> resultVideos = videoService.getByVideoIdIn(videoIds);

      // then
      assertNotNull(resultVideos, "결과가 null이면 안됩니다.");
      assertEquals(expectedVideos.size(), resultVideos.size(), "리스트의 크기가 서로 다릅니다.");
      assertTrue(resultVideos.containsAll(expectedVideos), "값이 올바르지 않습니다.");
    }

    @Test
    @DisplayName("비디오 id로 조회 성공")
    void id조회성공() {
      // given
      Long existingVideoId = TEST_VIDEO_ID;
      Video expectedVideo = Video.builder()
          .videoId(existingVideoId)
          .title(TEST_TITLE)
          .description(TEST_DESCRIPTION)
          .ratingType(TEST_RATING_TYPE)
          .build();
      given(videoJpaRepository.findById(existingVideoId)).willReturn(Optional.of(expectedVideo));

      // when
      Video foundVideo = videoService.getByVideoId(existingVideoId);

      // then
      assertNotNull(foundVideo);
      assertEquals(expectedVideo.getVideoId(), foundVideo.getVideoId());
      assertEquals(expectedVideo.getTitle(), foundVideo.getTitle());
    }

    @Test
    @DisplayName("비디오 id로 조회 실패")
    void id조회실패() {
      // given
      Long nonExistingVideoId = TEST_VIDEO_ID;
      given(videoJpaRepository.findById(nonExistingVideoId)).willReturn(Optional.empty());

      // when & then
      assertThrows(EntityNotFoundException.class,
          () -> videoService.getByVideoId(nonExistingVideoId));
    }

  }

  @Nested
  @DisplayName("ID 확인")
  class existsByIds {

    @Test
    @DisplayName("비디오 ID로 확인 실패")
    void id확인성공() {
      // given
      Long existingVideoId = TEST_VIDEO_ID;
      given(videoJpaRepository.existsById(existingVideoId)).willReturn(true);

      // when
      assertDoesNotThrow(() -> videoService.validateVideoExists(existingVideoId));

      // then
      verify(videoJpaRepository).existsById(existingVideoId);
    }

    @Test
    @DisplayName("비디오 ID로 확인 성공")
    void id확인실패() {
      // given
      Long nonExistingVideoId = TEST_VIDEO_ID;
      given(videoJpaRepository.existsById(nonExistingVideoId)).willReturn(false);

      // when & then
      assertThrows(EntityNotFoundException.class,
          () -> videoService.validateVideoExists(nonExistingVideoId));
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

  private Video setupMockVideoWithCategories() {
    // Setup categories
    Category categoryAnime = Category.builder()
        .categoryId(1L)
        .type(CategoryType.ANIME)
        .genres(Set.of(GenreType.ACTION, GenreType.FANTASY))
        .build();
    Category categoryDrama = Category.builder()
        .categoryId(2L)
        .type(CategoryType.DRAMA)
        .genres(Set.of(GenreType.ROMANCE))
        .build();

    Video video = Video.builder()
        .videoId(TEST_VIDEO_ID)
        .title(TEST_TITLE)
        .description(TEST_DESCRIPTION)
        .ratingType(TEST_RATING_TYPE)
        .build();

    VideoCategory videoCategoryAnime = new VideoCategory(video, categoryAnime);
    VideoCategory videoCategoryDrama = new VideoCategory(video, categoryDrama);

    video.addVideoCategory(videoCategoryAnime);
    video.addVideoCategory(videoCategoryDrama);

    return video;
  }

}
