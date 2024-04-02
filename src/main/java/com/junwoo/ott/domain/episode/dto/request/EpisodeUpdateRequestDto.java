package com.junwoo.ott.domain.episode.dto.request;

import com.junwoo.ott.domain.episode.dto.body.EpisodeUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EpisodeUpdateRequestDto {

    private final Long episodeId;
    private final EpisodeUpdateDto dto;

}
