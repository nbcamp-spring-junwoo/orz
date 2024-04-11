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

    public Episode getEpisodeById(Long episodeId) {
        return episodeRepository.findById(episodeId)
            .orElseThrow(() -> new EntityNotFoundException("에피소드를 찾을 수 없습니다."));
    }

    public boolean canUserAccessEpisode(UserDetailsImpl userDetails, Long videoId, Long episodeId) {
        Episode episode = getEpisodeById(episodeId);

        if (!episode.getVideo().getVideoId().equals(videoId)) {
            throw new EntityNotFoundException("에피소드가 지정된 비디오에 없습니다.");
        }

        MembershipType userMembership = userDetails.getMembershipType();
        MembershipType requiredMembership = episode.getMembershipType();

        return isAccessAllowed(userMembership, requiredMembership);
    }
    private boolean isAccessAllowed(MembershipType userMembership, MembershipType requiredMembership) {
        if (userMembership.equals(MembershipType.ROLE_GOLD)) {
            return true;
        } else if (userMembership.equals(MembershipType.ROLE_SILVER)) {
            return !requiredMembership.equals(MembershipType.ROLE_GOLD);
        } else if (userMembership.equals(MembershipType.ROLE_BRONZE)) {
            return requiredMembership.equals(MembershipType.ROLE_BRONZE) || requiredMembership.equals(MembershipType.ROLE_NORMAL);
        } else {
            return requiredMembership.equals(MembershipType.ROLE_NORMAL);
        }
    }

}
