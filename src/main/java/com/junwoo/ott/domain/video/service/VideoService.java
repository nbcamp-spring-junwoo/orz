package com.junwoo.ott.domain.video.service;

import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoCreateResponseDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.repository.VideoJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@RequiredArgsConstructor
@Service
public class VideoService {

  private final VideoJpaRepository videoJpaRepository;

  public VideoCreateResponseDto createVideo(VideoCreateRequestDto dto) {
    Video video = Video.builder()
        .title(dto.getTitle())
        .description(dto.getDescription())
        .ratingType(dto.getRatingType())
        .build();

    videoJpaRepository.save(video);

    return new VideoCreateResponseDto(video);
  }

}
