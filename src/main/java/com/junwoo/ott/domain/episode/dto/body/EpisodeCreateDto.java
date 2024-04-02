package com.junwoo.ott.domain.episode.dto.body;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EpisodeCreateDto {

    @NotNull
    private final String title;

    @NotNull
    private final LocalDateTime releasedAt;

}
