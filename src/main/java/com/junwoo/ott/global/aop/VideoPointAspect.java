package com.junwoo.ott.global.aop;

import com.junwoo.ott.domain.chart.service.ChartHistoryService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Slf4j(topic = "VideoPointAspect")
@Aspect
@AllArgsConstructor
@Component
public class VideoPointAspect {

  private final ChartHistoryService chartHistoryService;

  @AfterReturning(value = "@annotation(videoPoint) && args(videoId)")
  public void addVideoPoint(VideoPoint videoPoint, Long videoId) {
    double points = videoPoint.points();

    chartHistoryService.addVideoPoint(videoId, points);
    log.info("video ID : {} | points : {}", videoId, points);
  }

}

