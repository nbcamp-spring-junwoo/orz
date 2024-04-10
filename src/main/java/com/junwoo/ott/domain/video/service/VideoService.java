package com.junwoo.ott.domain.video.service;

import com.junwoo.ott.domain.category.dto.body.CategoryInfoDto;
import com.junwoo.ott.domain.category.entity.Category;
import com.junwoo.ott.domain.category.entity.VideoCategory;
import com.junwoo.ott.domain.category.service.CategoryService;
import com.junwoo.ott.domain.category.service.VideoCategoryService;
import com.junwoo.ott.domain.video.dto.body.VideoSearchByCategoryDto;
import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoReadRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoUpdateRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoCreateResponseDto;
import com.junwoo.ott.domain.video.dto.response.VideoReadResponseDto;
import com.junwoo.ott.domain.video.dto.response.VideoUpdateResponseDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.repository.VideoJpaRepository;
import com.junwoo.ott.global.exception.custom.TitleNotFoundException;
import jakarta.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class VideoService {

  private final VideoJpaRepository videoJpaRepository;
  private final CategoryService categoryService;
  private final VideoCategoryService videoCategoryService;

  public VideoCreateResponseDto createVideo(VideoCreateRequestDto requestDto) {
    Video video = Video.builder()
        .title(requestDto.getTitle())
        .description(requestDto.getDescription())
        .ratingType(requestDto.getRatingType())
        .build();
    video = videoJpaRepository.save(video);

    Set<Long> savedCategoryIds = new HashSet<>();
    for (Long categoryId : requestDto.getCategoryIds()) {
      Category category = categoryService.existsCategoryById(categoryId);
      videoCategoryService.associateVideoWithCategory(video, category);
      savedCategoryIds.add(categoryId);
    }

    return new VideoCreateResponseDto(video, savedCategoryIds);
  }

  @Transactional(readOnly = true)
  public Page<VideoReadResponseDto> getVideos(final VideoReadRequestDto dto) {
    Page<Video> videosPage = videoJpaRepository.getVideos(dto.getPageable());
    return videosPage.map(this::mapToVideoReadResponseDto);
  }

  @Transactional(readOnly = true)
  public VideoReadResponseDto getVideo(final Long videoId) {
    Video video = videoJpaRepository.findById(videoId)
        .orElseThrow(() -> new EntityNotFoundException("Video not found: " + videoId));

    Set<CategoryInfoDto> categoryInfos = fetchCategoryInfosForVideo(video);

    return new VideoReadResponseDto(video, categoryInfos);
  }

  @Transactional(readOnly = true)
  public Page<VideoReadResponseDto> getVideosByTitle(final VideoReadRequestDto dto) {
    if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
      Page<Video> videosPage = videoJpaRepository.findByTitle(dto.getTitle(), dto.getPageable());
      return videosPage.map(this::mapToVideoReadResponseDto);
    } else {
      throw new TitleNotFoundException("제목을 찾을 수 없습니다.");
    }

  }

  @Transactional(readOnly = true)
  public Page<VideoReadResponseDto> getVideosByCategory(
      final VideoSearchByCategoryDto searchDto,
      Pageable pageable) {
    Page<VideoCategory> videoCategoriesPage = videoCategoryService.findVideosByCategory(searchDto.getCategoryType(), searchDto.getGenres(), pageable);

    List<VideoReadResponseDto> videoDtos = videoCategoriesPage.getContent().stream()
        .map(vc -> mapToVideoReadResponseDto(vc.getVideo()))
        .collect(Collectors.toList());

    return new PageImpl<>(videoDtos, pageable, videoCategoriesPage.getTotalElements());
  }

  public VideoUpdateResponseDto updateVideo(final VideoUpdateRequestDto updateRequestDto) {
    Video video = existVideoById(updateRequestDto.getVideoId());
    video.update(updateRequestDto.getDto().getTitle(), updateRequestDto.getDto().getDescription(), updateRequestDto.getDto().getRatingType());
    videoJpaRepository.save(video);

    videoCategoryService.removeCategoriesByVideo(video);

    Set<Long> updatedCategoryIds = new HashSet<>();
    for (Long categoryId : updateRequestDto.getDto().getCategoryIds()) {
      Category category = categoryService.existsCategoryById(categoryId);
      videoCategoryService.associateVideoWithCategory(video, category);
      updatedCategoryIds.add(categoryId);
    }

    return new VideoUpdateResponseDto(video, updatedCategoryIds);
  }

  public void deleteVideo(final Long videoId) {
    boolean exists = videoJpaRepository.existsById(videoId);
    if (!exists) {
      throw new EntityNotFoundException("비디오 id가 존재하지 않습니다.");
    }

    videoJpaRepository.softDeleteVideoById(videoId);
  }

  @Transactional(readOnly = true)
  public Video existVideoById(final Long videoId) {
    return videoJpaRepository.findById(videoId).orElseThrow(
        () -> new EntityNotFoundException("비디오 id가 존재하지 않습니다.")
    );
  }

  public List<Video> getByVideoIdIn(final List<Long> videoIds) {
    return videoJpaRepository.findAllById(videoIds);
  }

  public Video getByVideoId(final Long videoId) {
    return videoJpaRepository.findById(videoId)
        .orElseThrow(() -> new EntityNotFoundException("비디오 id가 존재하지 않습니다."));

  }

  public void validateVideoExists(final Long videoId) {
    boolean exists = videoJpaRepository.existsById(videoId);
    if (!exists) {
      throw new EntityNotFoundException("비디오 id가 존재하지 않습니다.");
    }

  }

  private Set<CategoryInfoDto> fetchCategoryInfosForVideo(Video video) {

    return video.getVideoCategories().stream()
        .map(vc -> new CategoryInfoDto(
            vc.getCategory().getCategoryId(),
            vc.getCategory().getType(),
            vc.getCategory().getGenres()))
        .collect(Collectors.toSet());
  }

  private VideoReadResponseDto mapToVideoReadResponseDto(Video video) {
    Set<CategoryInfoDto> categoryInfos = fetchCategoryInfosForVideo(video);
    return new VideoReadResponseDto(
        video.getVideoId(),
        video.getTitle(),
        video.getDescription(),
        video.getRatingType(),
        video.getCreatedAt(),
        video.getUpdatedAt(),
        categoryInfos
    );
  }

}
