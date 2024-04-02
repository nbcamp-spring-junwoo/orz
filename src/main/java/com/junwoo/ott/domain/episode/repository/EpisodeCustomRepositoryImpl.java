package com.junwoo.ott.domain.episode.repository;

import com.junwoo.ott.domain.episode.entity.Episode;
import com.junwoo.ott.domain.episode.entity.QEpisode;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class EpisodeCustomRepositoryImpl implements EpisodeCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Episode> findByEpisodeId(final Long videoId, final Pageable pageable) {
        QEpisode qEpisode = QEpisode.episode;
        List<Episode> episodes = queryFactory.selectFrom(qEpisode)
                .where(qEpisode.video.videoId.eq(videoId), qEpisode.deletedAt.isNull())
                .orderBy(qEpisode.releasedAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(qEpisode)
                .where(qEpisode.video.videoId.eq(videoId), qEpisode.deletedAt.isNull())
                .fetch()
                .size();

        return new PageImpl<>(episodes, pageable, total);
    }

    @Override
    public Optional<Episode> findByVideoIdAndEpisodeId(final Long videoId, final Long episodeId) {
        QEpisode qEpisode = QEpisode.episode;
        Episode episode = queryFactory.selectFrom(qEpisode)
            .where(qEpisode.video.videoId.eq(videoId),
                qEpisode.episodeId.eq(episodeId),
                qEpisode.deletedAt.isNull())
            .fetchOne();

        return Optional.ofNullable(episode);
    }

}
