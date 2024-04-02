package com.junwoo.ott.domain.episode.dto.response;

import com.junwoo.ott.domain.episode.entity.Episode;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EpisodeReadResponseDto {

    private final Long episodeId;
    private final String title;
    private final LocalDateTime releasedAt;
    private final Long videoId;

    public EpisodeReadResponseDto(final Episode episode) {
        this.episodeId = episode.getEpisodeId();
        this.title = episode.getTitle();
        this.releasedAt = episode.getReleasedAt();
        this.videoId = episode.getVideo() != null ? episode.getVideo().getVideoId() : null;
    }

}
