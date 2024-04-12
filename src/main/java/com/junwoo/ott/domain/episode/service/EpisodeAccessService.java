package com.junwoo.ott.domain.episode.service;

import com.junwoo.ott.domain.episode.entity.Episode;
import com.junwoo.ott.domain.episode.repository.EpisodeRepository;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EpisodeAccessService {

  private final EpisodeRepository episodeRepository;

  public Episode getEpisodeById(final Long episodeId) {
    return episodeRepository
        .findById(episodeId)
        .orElseThrow(() -> new EntityNotFoundException("에피소드를 찾을 수 없습니다."));
  }

  public boolean canUserAccessEpisode(final UserDetailsImpl userDetails, final Long videoId, final Long episodeId) {
    Episode episode = getEpisodeById(episodeId);

    if (!episode.getVideo().getVideoId().equals(videoId)) {
      throw new EntityNotFoundException("에피소드가 지정된 비디오에 없습니다.");
    }

    MembershipType userMembership = userDetails.getMembershipType();
    MembershipType requiredMembership = episode.getMembershipType();

    return isAccessAllowed(userMembership, requiredMembership);
  }

  private boolean isAccessAllowed(
      final MembershipType userMembership, final MembershipType requiredMembership
  ) {
    return userMembership.compareTo(requiredMembership) >= 0;
  }
}
