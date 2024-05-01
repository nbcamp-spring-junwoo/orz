package com.junwoo.ott.domain.video.service;

import com.junwoo.ott.domain.video.dto.request.VideoReadRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoReadResponseDto;
import com.junwoo.ott.domain.video.dto.response.VideoResponseDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.repository.VideoRepository;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.exception.custom.VideoNotFoundException;
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

  @Transactional(readOnly = true)
  public Page<VideoResponseDto> getVideos(final VideoRequestDto videoRequestDto) {
    return videoRepository
        .findAllByFilter(videoRequestDto.getPageable(), videoRequestDto.getGenreTypeList(),
            videoRequestDto.getIsIntersection())
        .map(video -> new VideoResponseDto(
            video.get(0, Long.class),
            video.get(1, String.class),
            video.get(2, String.class),
            video.get(3, MembershipType.class))
        );
  }

  @Transactional(readOnly = true)
  public VideoReadResponseDto getVideo(final VideoReadRequestDto videoReadRequestDto) {

    if (!isExistVideo(videoReadRequestDto.getVideoId())) {
      throw new VideoNotFoundException("존재하지 않는 비디오 입니다");
    }

    return videoRepository.findOneQuery(videoReadRequestDto.getVideoId(),
        videoReadRequestDto.getPageable());
  }

  public List<Video> getByVideoIdIn(final List<Long> videoIds) {
    return videoRepository.findAllByVideoIdIn(videoIds);
  }

  private boolean isExistVideo(final Long videoId) {
    return videoRepository.existsById(videoId);
  }

}
