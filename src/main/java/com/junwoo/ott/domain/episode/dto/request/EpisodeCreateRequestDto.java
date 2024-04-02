package com.junwoo.ott.domain.episode.dto.request;

import com.junwoo.ott.domain.episode.dto.body.EpisodeCreateDto;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EpisodeCreateRequestDto {

    private final String title;
    private final LocalDateTime releasedAt;

    public EpisodeCreateRequestDto(EpisodeCreateDto dto) {
        this.title = dto.getTitle();
        this.releasedAt = dto.getReleasedAt();
    }

}
