package com.junwoo.ott.domain.episode.service;

import com.junwoo.ott.domain.episode.dto.request.EpisodeCreateRequestDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeCreateResponseDto;
import com.junwoo.ott.domain.episode.entity.Episode;
import com.junwoo.ott.domain.episode.repository.EpisodeRepository;
import com.junwoo.ott.domain.video.service.VideoAdminService;
import com.junwoo.ott.global.exception.custom.VideoNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Transactional
@Service
public class EpisodeAdminService {

  private final VideoAdminService videoAdminService;

  private final EpisodeRepository episodeRepository;

  public EpisodeCreateResponseDto postEpisode(final EpisodeCreateRequestDto episodeCreateRequestDto) {

    validateVideo(episodeCreateRequestDto.getVideoId());

    Episode episode = Episode.builder()
        .videoId(episodeCreateRequestDto.getVideoId())
        .title(episodeCreateRequestDto.getTitle())
        .description(episodeCreateRequestDto.getDescription())
        .releasedAt(episodeCreateRequestDto.getReleasedAt())
        .build();

    return new EpisodeCreateResponseDto(episodeRepository.save(episode));
  }

  private void validateVideo(final Long videoId) {
    if (!videoAdminService.isExistVideo(videoId)) {
      throw new VideoNotFoundException("비디오가 존재하지 않습니다.");
    }
  }

}
