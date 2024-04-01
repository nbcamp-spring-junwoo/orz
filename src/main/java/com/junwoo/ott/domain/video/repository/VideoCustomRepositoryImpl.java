package com.junwoo.ott.domain.video.repository;

import com.junwoo.ott.domain.video.entity.QVideo;
import com.junwoo.ott.domain.video.entity.Video;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VideoCustomRepositoryImpl implements VideoCustomRepository {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Video> findByTitle(String title, Pageable pageable) {
        QVideo video = QVideo.video;
        List<Video> videos = queryFactory.selectFrom(video)
                .where(titleEq(title), video.deletedAt.isNull())
                .orderBy(video.createdAt.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory.selectFrom(video)
                .where(titleEq(title), video.deletedAt.isNull())
                .fetch()
                .size();

        return new PageImpl<>(videos, pageable, total);
    }

    @Override
    public Page<Video> getVideos(Pageable pageable) {
        QVideo qVideo = QVideo.video;
        List<Video> videos = queryFactory.selectFrom(qVideo)
            .where(qVideo.deletedAt.isNull())
            .orderBy(qVideo.createdAt.desc())
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        long total = queryFactory.selectFrom(qVideo)
            .where(qVideo.deletedAt.isNull())
            .fetch()
            .size();

        return new PageImpl<>(videos, pageable, total);
    }

    private BooleanExpression titleEq(String title) {

        if (title == null || title.isEmpty()) {
            return null;
        }

        return QVideo.video.title.eq(title);
    }

}