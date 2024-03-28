package com.junwoo.ott.domain.episode.repository;

import com.junwoo.ott.domain.episode.entity.Episode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodeRepository extends JpaRepository<Episode, Long> {

}
