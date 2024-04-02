package com.junwoo.ott.domain.episode.service;

import com.junwoo.ott.domain.episode.dto.request.EpisodeCreateRequestDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeCreateResponseDto;
import com.junwoo.ott.domain.episode.entity.Episode;
import com.junwoo.ott.domain.episode.repository.EpisodeRepository;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    private final VideoService videoService;

    public EpisodeCreateResponseDto createEpisode(Long videoId, EpisodeCreateRequestDto dto) {
        Video video = videoService.getByVideoId(videoId);

        Episode episode = Episode.builder()
            .title(dto.getTitle())
            .releasedAt(dto.getReleasedAt())
            .video(video)
            .build();

        episode = episodeRepository.save(episode);

        return new EpisodeCreateResponseDto(episode);
    }

}
