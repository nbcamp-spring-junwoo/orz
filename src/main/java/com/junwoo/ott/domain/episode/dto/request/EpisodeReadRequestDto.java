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

  public EpisodeReadRequestDto(Long videoId, Pageable pageable) {
    this.videoId = videoId;
    this.pageable = pageable;
  }

  public EpisodeReadRequestDto(Long videoId, Long episodeId) {
    this.videoId = videoId;
    this.episodeId = episodeId;

  }

  public EpisodeReadRequestDto(Long videoId, Pageable pageable, MembershipType membershipType) {
    this.videoId = videoId;
    this.pageable = pageable;
    this.membershipType = membershipType;
  }

  public EpisodeReadRequestDto(Long videoId, Long episodeId, MembershipType membershipType) {
    this.videoId = videoId;
    this.episodeId = episodeId;
    this.membershipType = membershipType;
  }

}
