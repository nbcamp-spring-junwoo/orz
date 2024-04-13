package com.junwoo.ott.domain.video.service;

import com.junwoo.ott.domain.video.dto.request.VideoRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoResponseDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.repository.VideoRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class VideoService {

  private final VideoRepository videoRepository;

  public Page<VideoResponseDto> getVideos(final VideoRequestDto videoRequestDto) {
    return videoRepository.findAll(videoRequestDto.getPageable())
        .map(video -> new VideoResponseDto(
            video.getVideoId(),
            video.getTitle(),
            video.getDescription(),
            video.getPosterUrl(),
            video.getMembershipType())
        );
  }

  public List<Video> getByVideoIdIn(final List<Long> videoIds) {
    return null;
  }

}
