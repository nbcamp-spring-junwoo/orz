package com.junwoo.ott.domain.episode.dto.body;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EpisodeUpdateDto {

    private final String title;
    private final LocalDateTime releasedAt;

}
