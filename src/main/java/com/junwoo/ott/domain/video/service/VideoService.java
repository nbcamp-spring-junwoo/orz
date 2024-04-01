package com.junwoo.ott.domain.video.service;

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
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class VideoService {

  private final VideoJpaRepository videoJpaRepository;

  public VideoCreateResponseDto createVideo(VideoCreateRequestDto dto) {
    //어드민 인증
    Video video = Video.builder()
        .title(dto.getTitle())
        .description(dto.getDescription())
        .ratingType(dto.getRatingType())
        .build();

    videoJpaRepository.save(video);

    return new VideoCreateResponseDto(video);
  }

  @Transactional(readOnly = true)
  public Page<VideoReadResponseDto> getVideos(VideoReadRequestDto dto) {
    Page<Video> videosPage = videoJpaRepository.getVideos(dto.getPageable());
    return videosPage.map(VideoReadResponseDto::new);
  }

  @Transactional(readOnly = true)
  public VideoReadResponseDto getVideo(Long videoId) {
    //회원 인증
    return videoJpaRepository.findById(videoId)
        .map(VideoReadResponseDto::new)
        .orElseThrow(() -> new EntityNotFoundException("비디오 id를 찾을 수 없습니다.: " + videoId));
  }

  @Transactional(readOnly = true)
  public Page<VideoReadResponseDto> getVideosByTitle(VideoReadRequestDto dto) {
    // 회원 인증
    if (dto.getTitle() != null && !dto.getTitle().isEmpty()) {
      Page<Video> videosPage = videoJpaRepository.findByTitle(dto.getTitle(),
          dto.getPageable());
      return videosPage.map(VideoReadResponseDto::new);
    } else {
      throw new TitleNotFoundException("제목을 찾을 수 없습니다.");
    }

  }

  public VideoUpdateResponseDto updateVideo(VideoUpdateRequestDto updateRequestDto) {
    Video video = existVideoById(updateRequestDto.getVideoId());
    Video updatedVideo = video.update(updateRequestDto.getDto());
    videoJpaRepository.save(updatedVideo);

    return new VideoUpdateResponseDto(updatedVideo);
  }

  public void deleteVideo(Long videoId) {
    Video video = existVideoById(videoId);
    video.Deleted();
    videoJpaRepository.save(video);
  }

  @Transactional(readOnly = true)
  public Video existVideoById(Long videoId) {
    return videoJpaRepository.findById(videoId).orElseThrow(
        () -> new EntityNotFoundException("비디오 id가 존재하지 않습니다.")
    );
  }

  public List<Video> getByVideoIdIn(List<Long> videoIds) {
    return videoJpaRepository.findAllById(videoIds);
  }

}
