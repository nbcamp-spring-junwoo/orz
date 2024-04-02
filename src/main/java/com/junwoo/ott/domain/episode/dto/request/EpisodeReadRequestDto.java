package com.junwoo.ott.domain.episode.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class EpisodeReadRequestDto {

    private Long episodeId;
    private String title;
    private Pageable pageable;

    public EpisodeReadRequestDto(Pageable pageable) {
        this.pageable = pageable;
    }

    public EpisodeReadRequestDto(String title, Pageable pageable) {
        this.title = title;
        this.pageable = pageable;
    }

}
