package com.junwoo.ott.domain.video.service;

import com.junwoo.ott.domain.genre.service.VideoGenreService;
import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoReadRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoCreateResponseDto;
import com.junwoo.ott.domain.video.dto.response.VideoReadResponseDto;
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

  private final VideoGenreService videoGenreService;
  private final VideoService videoService;

  private final VideoRepository videoRepository;



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
    return videoService.getVideos(videoRequestDto);
  }

  public VideoReadResponseDto getVideo(final VideoReadRequestDto videoReadRequestDto) {
    return videoService.getVideo(videoReadRequestDto);
  }

  public boolean isExistVideo(final Long videoId) {
    return videoRepository.existsById(videoId);
  }

}
