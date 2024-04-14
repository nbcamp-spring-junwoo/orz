package com.junwoo.ott.domain.chart.repository;

import static com.junwoo.ott.domain.chart.entity.QChart.chart;
import static com.junwoo.ott.domain.video.entity.QVideo.video;

import com.junwoo.ott.domain.chart.dto.response.ChartResponseDto;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ChartQueryRepositoryImpl implements ChartQueryRepository {

  private final JPAQueryFactory factory;

  @Override
  public List<ChartResponseDto> getAllChartsWithVideoInfo() {

    return factory
        .select(Projections.constructor(ChartResponseDto.class,
            chart.video.videoId,
            chart.video.title,
            chart.video.description,
            // chart.video.releasedAt,
            chart.video.posterUrl
        ))
        .from(chart)
        .join(chart.video, video)
        .orderBy(chart.point.desc())
        .fetch();
  }

}
