package com.junwoo.ott.domain.episode.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class EpisodeCustomRepositoryImpl implements EpisodeCustomRepository {

  private final JPAQueryFactory queryFactory;

}
