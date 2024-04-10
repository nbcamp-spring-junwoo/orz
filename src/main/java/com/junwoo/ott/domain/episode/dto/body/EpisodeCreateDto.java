package com.junwoo.ott.domain.episode.dto.body;

import com.junwoo.ott.global.customenum.MembershipType;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class EpisodeCreateDto {

    @NotNull
    private final Long videoId;
    @NotNull
    private final String title;
    @NotNull
    private final LocalDateTime releasedAt;
    @NotNull
    private final MembershipType membershipType;
    @NotNull
    private final String videoLink;

}
