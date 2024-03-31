package com.junwoo.ott.domain.chart.service;

import com.junwoo.ott.domain.chart.dto.response.VideoPointResponseDto;
import jakarta.annotation.PostConstruct;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.data.redis.core.ZSetOperations.TypedTuple;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ChartHistoryService {

  public static final String VIDEO_POINT = "videoPoint";
  public static final Integer TOP_N = 10;

  private final RedisTemplate<String, String> redisTemplate;
  private ZSetOperations<String, String> zSetOps;

  @PostConstruct
  void init() {
    zSetOps = redisTemplate.opsForZSet();
  }

  public void addVideoPoint(Long videoId, double point) {
    zSetOps.add(VIDEO_POINT, String.valueOf(videoId), point);
  }

  public List<VideoPointResponseDto> getTopVideos() {
    Set<TypedTuple<String>> topVideosWithScores = Optional.ofNullable(
            zSetOps.reverseRangeWithScores(VIDEO_POINT, 0, TOP_N - 1))
        .orElse(Collections.emptySet());

    return topVideosWithScores.stream()
        .map(tuple -> new VideoPointResponseDto(Long.parseLong(tuple.getValue()), tuple.getScore()))
        .toList();
  }

  public void deleteVideoPoint() {
    redisTemplate.delete(VIDEO_POINT);
  }

}
