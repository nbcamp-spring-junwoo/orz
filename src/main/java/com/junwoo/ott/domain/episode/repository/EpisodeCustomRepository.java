package com.junwoo.ott.domain.episode.repository;

import com.junwoo.ott.domain.episode.entity.Episode;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EpisodeCustomRepository {

    Page<Episode> findByEpisodeId(final Long videoId, final Pageable pageable);

    Optional<Episode> findByVideoIdAndEpisodeId(final Long videoId, final Long episodeId);

}
