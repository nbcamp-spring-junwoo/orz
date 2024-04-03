package com.junwoo.ott.domain.episode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class EpisodeReadRequestDto {

    private Long videoId;
    private Long episodeId;
    private String title;
    private Pageable pageable;

    public EpisodeReadRequestDto(Long videoId, Pageable pageable) {
        this.videoId = videoId;
        this.pageable = pageable;
    }

    public EpisodeReadRequestDto(Long videoId, Long episodeId) {
        this.videoId = videoId;
        this.episodeId = episodeId;

    }
}
