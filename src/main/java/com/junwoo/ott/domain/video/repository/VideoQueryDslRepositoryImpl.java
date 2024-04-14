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
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
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
        .select(new QVideoProjectionDto(
            video.videoId,
            video.title,
            video.description,
            video.posterUrl,
            video.membershipType))
        .from(video)
        .where(video.videoId.eq(videoId))
        .fetchOne();

    List<GenreType> genreList = jpaQueryFactory.select(genre.genreType)
        .from(genre)
        .join(videoGenre).on(videoGenre.videoId.eq(videoId))
        .where(videoGenre.genreId.eq(genre.genreId))
        .fetch();

    List<EpisodeProjectionDto> episodeProjectionDtoList = jpaQueryFactory.select(episode)
        .select(new QEpisodeProjectionDto(
            episode.episodeId,
            episode.title,
            episode.description,
            episode.releasedAt))
        .from(episode)
        .where(episode.videoId.eq(videoId))
        .orderBy(episode.releasedAt.desc())
        .fetch();

    JPAQuery<Long> count = jpaQueryFactory
        .select(episode.count())
        .from(episode);

    return new VideoReadResponseDto(videoProjectionDto, genreList,
        PageableExecutionUtils.getPage(episodeProjectionDtoList, pageable, count::fetchOne));
  }

}
