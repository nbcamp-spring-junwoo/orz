package com.junwoo.ott.domain.episode.dto.body;

import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EpisodeCreateDto {

    @NotNull
    private String title;

    @NotNull
    private LocalDateTime releasedAt;

}