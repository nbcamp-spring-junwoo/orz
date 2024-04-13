package com.junwoo.ott.domain.video.service;

import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoCreateResponseDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.repository.VideoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class VideoService {

  private final VideoRepository videoRepository;

  public VideoCreateResponseDto postVideo(final VideoCreateRequestDto videoCreateRequestDto) {
    Video video = Video.builder()
        .title(videoCreateRequestDto.getTitle())
        .description(videoCreateRequestDto.getDescription())
        .ratingType(videoCreateRequestDto.getRatingType())
        .videoUrl(videoCreateRequestDto.getVideoUrl())
        .build();

    return new VideoCreateResponseDto(videoRepository.save(video));

  }

  public List<Video> getByVideoIdIn(final List<Long> videoIds) {
    return null;
  }


}
