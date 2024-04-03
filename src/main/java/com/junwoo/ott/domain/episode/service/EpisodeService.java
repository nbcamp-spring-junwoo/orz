package com.junwoo.ott.domain.episode.service;

import com.junwoo.ott.domain.episode.dto.request.EpisodeCreateRequestDto;
import com.junwoo.ott.domain.episode.dto.request.EpisodeReadRequestDto;
import com.junwoo.ott.domain.episode.dto.request.EpisodeUpdateRequestDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeCreateResponseDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeReadResponseDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeUpdateResponseDto;
import com.junwoo.ott.domain.episode.entity.Episode;
import com.junwoo.ott.domain.episode.repository.EpisodeRepository;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.service.VideoService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class EpisodeService {

    private final EpisodeRepository episodeRepository;

    private final VideoService videoService;

    public EpisodeCreateResponseDto createEpisode(
        final EpisodeCreateRequestDto dto
    ) {
        Video video = videoService.getByVideoId(dto.getVideoId());

        Episode episode = Episode.builder()
            .title(dto.getTitle())
            .releasedAt(dto.getReleasedAt())
            .video(video)
            .build();

        episode = episodeRepository.save(episode);

        return new EpisodeCreateResponseDto(episode);
    }

    public Page<EpisodeReadResponseDto> getEpisodesByVideo(final EpisodeReadRequestDto requestDto) {
        videoService.validateVideoExists(requestDto.getVideoId());
        Page<Episode> episodesPage = episodeRepository.findByEpisodeId(requestDto.getVideoId(), requestDto.getPageable());
        return episodesPage.map(EpisodeReadResponseDto::new);
    }

    public EpisodeReadResponseDto getEpisodeByVideo(final EpisodeReadRequestDto dto) {
        videoService.validateVideoExists(dto.getVideoId());
        Episode episode = episodeRepository.findByVideoIdAndEpisodeId(dto.getVideoId(), dto.getEpisodeId())
            .orElseThrow(() -> new EntityNotFoundException("에피소드 id를 찾을 수 없습니다."));

        return new EpisodeReadResponseDto(episode);
    }

    public EpisodeUpdateResponseDto updateEpisode(final EpisodeUpdateRequestDto updateRequestDto) {
        Episode episode = episodeRepository.findById(updateRequestDto.getEpisodeId())
            .orElseThrow(() -> new EntityNotFoundException("에피소드 id가 존재하지 않습니다."));

        episode.update(updateRequestDto.getDto());
        episode = episodeRepository.save(episode);

        return new EpisodeUpdateResponseDto(episode);
    }

    public void deleteEpisode(final Long episodeId) {
        boolean exists = episodeRepository.existsById(episodeId);
        if (!exists) {
            throw new EntityNotFoundException("에피소드 id가 존재하지 않습니다.");
        }

        episodeRepository.softDeleteEpisodeById(episodeId);
    }

}
