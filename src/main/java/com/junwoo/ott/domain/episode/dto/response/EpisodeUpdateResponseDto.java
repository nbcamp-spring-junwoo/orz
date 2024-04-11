package com.junwoo.ott.domain.episode.dto.response;

import com.junwoo.ott.domain.episode.entity.Episode;
import com.junwoo.ott.global.customenum.MembershipType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EpisodeUpdateResponseDto {

    private final Long episodeId;
    private final String title;
    private final LocalDateTime releasedAt;
    private final MembershipType membershipType;
    private final String videoLink;

    public EpisodeUpdateResponseDto(Episode episode) {
        this.episodeId = episode.getEpisodeId();
        this.title = episode.getTitle();
        this.releasedAt = episode.getReleasedAt();
        this.membershipType = episode.getMembershipType();
        this.videoLink = episode.getVideoLink();
    }

}
