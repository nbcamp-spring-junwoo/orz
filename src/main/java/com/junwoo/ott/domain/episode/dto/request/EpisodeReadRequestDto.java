package com.junwoo.ott.domain.episode.dto.request;

import com.junwoo.ott.global.customenum.MembershipType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class EpisodeReadRequestDto {

  private Long videoId;
  private Long episodeId;
  private String title;
  private Pageable pageable;
  private MembershipType membershipType;

  public EpisodeReadRequestDto(final Long videoId, final Pageable pageable) {
    this.videoId = videoId;
    this.pageable = pageable;
  }

  public EpisodeReadRequestDto(final Long videoId, final Long episodeId) {
    this.videoId = videoId;
    this.episodeId = episodeId;

  }

  public EpisodeReadRequestDto(final Long videoId, final Pageable pageable, final MembershipType membershipType) {
    this.videoId = videoId;
    this.pageable = pageable;
    this.membershipType = membershipType;
  }

  public EpisodeReadRequestDto(final Long videoId, final Long episodeId, final MembershipType membershipType) {
    this.videoId = videoId;
    this.episodeId = episodeId;
    this.membershipType = membershipType;
  }

}
