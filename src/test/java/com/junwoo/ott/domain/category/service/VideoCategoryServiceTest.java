package com.junwoo.ott.domain.category.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.junwoo.ott.domain.category.entity.Category;
import com.junwoo.ott.domain.category.entity.VideoCategory;
import com.junwoo.ott.domain.category.repository.VideoCategoryRepository;
import com.junwoo.ott.domain.category.test.VideoCategoryTestValues;
import com.junwoo.ott.domain.video.entity.Video;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

@ExtendWith(MockitoExtension.class)
class VideoCategoryServiceTest implements VideoCategoryTestValues {

  @Mock
  private VideoCategoryRepository videoCategoryRepository;

  @InjectMocks
  private VideoCategoryService videoCategoryService;

  @Test
  @DisplayName("비디오와 카테고리 연결 성공")
  void 비디오카테고리연결() {
    Video video = Video.builder()
        .videoId(TEST_VIDEO_ID)
        .build();
    Category category = Category.builder()
        .categoryId(TEST_CATEGORY_ID)
        .type(TEST_CATEGORY_TYPE)
        .genres(TEST_GENRES)
        .build();

    videoCategoryService.associateVideoWithCategory(video, category);

    verify(videoCategoryRepository).save(any(VideoCategory.class));
  }

  @Test
  @DisplayName("카테고리별 비디오 검색 성공")
  void 카테고리비디오검색성공() {
    Video video = Video.builder().videoId(TEST_VIDEO_ID).build();
    Category category = Category.builder()
        .categoryId(TEST_CATEGORY_ID)
        .type(TEST_CATEGORY_TYPE)
        .genres(TEST_GENRES)
        .build();
    List<VideoCategory> videoCategories = List.of(new VideoCategory(video, category));
    Page<VideoCategory> mockPage = new PageImpl<>(videoCategories);

    when(videoCategoryRepository.findByCategoryType(TEST_CATEGORY_TYPE, TEST_PAGEABLE)).thenReturn(mockPage);

    Page<VideoCategory> result = videoCategoryService.findVideosByCategory(TEST_CATEGORY_TYPE, TEST_GENRES, TEST_PAGEABLE);

    assertNotNull(result);
    assertEquals(1, result.getContent().size());
    verify(videoCategoryRepository).findByCategoryType(TEST_CATEGORY_TYPE, TEST_PAGEABLE);
  }

  @Test
  @DisplayName("비디오에 연결된 모든 카테고리 제거 성공")
  void 비디오의연결된카테고리제거() {
    Video video = Video.builder().videoId(TEST_VIDEO_ID).build();

    videoCategoryService.removeCategoriesByVideo(video);

    verify(videoCategoryRepository).deleteAllByVideo(video);
  }

}
