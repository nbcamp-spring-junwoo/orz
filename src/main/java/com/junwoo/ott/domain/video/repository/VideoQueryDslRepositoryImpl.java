package com.junwoo.ott.domain.video.repository;

import static com.junwoo.ott.domain.episode.entity.QEpisode.episode;
import static com.junwoo.ott.domain.genre.entity.QGenre.genre;
import static com.junwoo.ott.domain.genre.entity.QVideoGenre.videoGenre;
import static com.junwoo.ott.domain.video.entity.QVideo.video;

import com.junwoo.ott.domain.episode.dto.response.EpisodeProjectionDto;
import com.junwoo.ott.domain.episode.dto.response.QEpisodeProjectionDto;
import com.junwoo.ott.domain.video.dto.response.QVideoProjectionDto;
import com.junwoo.ott.domain.video.dto.response.VideoProjectionDto;
import com.junwoo.ott.domain.video.dto.response.VideoReadResponseDto;
import com.junwoo.ott.global.customenum.GenreType;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.Tuple;
import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.Collections;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class VideoQueryDslRepositoryImpl implements VideoQueryDslRepository {

  private final JPAQueryFactory jpaQueryFactory;

  @Override
  public VideoReadResponseDto findOneQuery(final Long videoId, final Pageable pageable) {

    VideoProjectionDto videoProjectionDto = jpaQueryFactory
        .select(
            new QVideoProjectionDto(video.videoId, video.title, video.description, video.posterUrl,
                video.releasedAt, video.membershipType))
        .from(video)
        .where(video.videoId.eq(videoId))
        .fetchOne();

    List<GenreType> genreList = jpaQueryFactory
        .select(genre.genreType)
        .from(genre)
        .join(videoGenre)
        .on(videoGenre.videoId.eq(videoId))
        .where(videoGenre.genreId.eq(genre.genreId))
        .fetch();

    List<EpisodeProjectionDto> episodeProjectionDtoList = jpaQueryFactory
        .select(episode)
        .select(new QEpisodeProjectionDto(episode.episodeId, episode.title, episode.description,
            episode.episodeUrl, episode.releasedAt))
        .from(episode)
        .where(episode.videoId.eq(videoId))
        .orderBy(episode.releasedAt.desc())
        .fetch();

    JPAQuery<Long> count = jpaQueryFactory.select(episode.count()).from(episode);

    return new VideoReadResponseDto(videoProjectionDto, genreList,
        PageableExecutionUtils.getPage(episodeProjectionDtoList, pageable, count::fetchOne));
  }

  @Override
  public Page<Tuple> findAllByFilter(
      final Pageable pageable, final List<GenreType> genreTypeList, final Boolean isIntersection
  ) {
    if (genreTypeList == null || genreTypeList.isEmpty()) {
      return PageableExecutionUtils.getPage(Collections.emptyList(), pageable, () -> 0L);
    }

    Predicate filterPredicate = filter(genreTypeList, isIntersection);

    List<Tuple> videoList = jpaQueryFactory
        .select(video.videoId, video.title, video.description, video.posterUrl,
            video.membershipType)
        .distinct()
        .from(video)
        .join(videoGenre)
        .on(video.videoId.eq(videoGenre.videoId))
        .join(genre)
        .on(genre.genreId.eq(videoGenre.genreId))
        .where(filterPredicate)
        .fetch();

    JPAQuery<Long> count = jpaQueryFactory
        .select(video.countDistinct())
        .from(video)
        .join(videoGenre)
        .on(video.videoId.eq(videoGenre.videoId))
        .join(genre)
        .on(genre.genreId.eq(videoGenre.genreId))
        .where(filterPredicate);

    return PageableExecutionUtils.getPage(videoList, pageable, count::fetchOne);
  }

  private Predicate filter(final List<GenreType> genreTypeList, final Boolean isIntersection) {
    BooleanBuilder builder = new BooleanBuilder();
    if (isIntersection) {
      genreTypeList.forEach(genreType -> builder.and(video.videoId.in(JPAExpressions
          .select(videoGenre.videoId)
          .from(videoGenre)
          .join(genre)
          .on(genre.genreId.eq(videoGenre.genreId))
          .where(genre.genreType.eq(genreType)))));
    } else {
      builder.or(genre.genreType.in(genreTypeList));
    }
    return builder;
  }

}
