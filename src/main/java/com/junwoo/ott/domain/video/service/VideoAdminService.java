package com.junwoo.ott.domain.video.service;

import com.junwoo.ott.domain.genre.service.VideoGenreService;
import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoCreateResponseDto;
import com.junwoo.ott.domain.video.dto.response.VideoResponseDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.repository.VideoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class VideoAdminService {

  private final VideoRepository videoRepository;

  private final VideoGenreService videoGenreService;

  public VideoCreateResponseDto postVideo(final VideoCreateRequestDto videoCreateRequestDto) {

    Video video = videoRepository.save(
        Video.builder()
            .title(videoCreateRequestDto.getTitle())
            .description(videoCreateRequestDto.getDescription())
            .membershipType(videoCreateRequestDto.getMembershipType())
            .posterUrl(videoCreateRequestDto.getPosterUrl())
            .releasedAt(videoCreateRequestDto.getReleasedAt())
            .build()
    );

    videoGenreService.createVideoGenre(video.getVideoId(), videoCreateRequestDto.getGenreTypeSet());

    return new VideoCreateResponseDto(video);
  }

  public Page<VideoResponseDto> getVideos(final VideoRequestDto videoRequestDto) {
    return videoRepository.findAll(
        videoRequestDto.getPageable()
    ).map(video -> new VideoResponseDto(
        video.getVideoId(),
        video.getTitle(),
        video.getDescription(),
        video.getPosterUrl(),
        video.getMembershipType()
    ));
  }

  public boolean isExistVideo(final Long videoId) {
    return videoRepository.existsById(videoId);
  }

}
