package com.junwoo.ott.domain.chart.service;

import com.junwoo.ott.domain.chart.dto.response.ChartResponseDto;
import com.junwoo.ott.domain.chart.dto.response.VideoPointResponseDto;
import com.junwoo.ott.domain.chart.entity.Chart;
import com.junwoo.ott.domain.chart.repository.ChartJpaRepository;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.service.VideoService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class ChartService {

  private final ChartHistoryService chartHistoryService;
  private final VideoService videoService;

  private final ChartJpaRepository chartJpaRepository;

  public List<ChartResponseDto> getChart() {

    return chartJpaRepository.getAllChartsWithVideoInfo();
  }

  public void updateChart() {
    List<VideoPointResponseDto> topVideos = chartHistoryService.getTopVideos();
    Map<Long, Double> videoIdToPointMap = topVideos.stream()
        .collect(
            Collectors.toMap(VideoPointResponseDto::getVideoId, VideoPointResponseDto::getPoint));

    chartHistoryService.deleteVideoPoint();
    deleteAllChart();

    List<Long> videoIds = new ArrayList<>(videoIdToPointMap.keySet());
    List<Video> videos = videoService.getByVideoIdIn(videoIds);

    List<Chart> charts = videos.stream()
        .map(video -> {
          Double point = videoIdToPointMap.get(video.getVideoId());
          return Chart.builder()
              .video(video)
              .point(point)
              .build();
        })
        .toList();

    chartJpaRepository.saveAll(charts);
  }

  @Transactional(propagation = Propagation.REQUIRES_NEW)
  protected void deleteAllChart() {
    chartJpaRepository.deleteAll();
    chartJpaRepository.flush();
  }
}
