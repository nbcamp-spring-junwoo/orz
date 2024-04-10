package com.junwoo.ott.domain.episode.dto.request;

import com.junwoo.ott.domain.episode.dto.body.EpisodeCreateDto;
import com.junwoo.ott.global.customenum.MembershipType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EpisodeCreateRequestDto {

    private final Long videoId;
    private final String title;
    private final LocalDateTime releasedAt;
    private final MembershipType membershipType;
    private final String videoLink;

    public EpisodeCreateRequestDto(EpisodeCreateDto dto) {
        this.videoId = dto.getVideoId();
        this.title = dto.getTitle();
        this.releasedAt = dto.getReleasedAt();
        this.membershipType = dto.getMembershipType();
        this.videoLink = dto.getVideoLink();
    }

    public EpisodeCreateRequestDto(EpisodeCreateDto dto, Long videoId) {
        this.videoId = videoId;
        this.title = dto.getTitle();
        this.releasedAt = dto.getReleasedAt();
        this.membershipType = dto.getMembershipType();
        this.videoLink = dto.getVideoLink();
    }

}
