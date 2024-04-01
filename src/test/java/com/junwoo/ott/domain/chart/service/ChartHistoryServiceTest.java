package com.junwoo.ott.domain.chart.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.junwoo.ott.domain.chart.test.ChartTestValues;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.redis.core.DefaultTypedTuple;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;

@ExtendWith(MockitoExtension.class)
public class ChartHistoryServiceTest implements ChartTestValues {

  @InjectMocks
  private ChartHistoryService chartHistoryService;
  @Mock
  private RedisTemplate<String, String> redisTemplate;
  @Mock
  private ZSetOperations<String, String> zSetOps;

  @BeforeEach
  void setUp() {
    given(redisTemplate.opsForZSet()).willReturn(zSetOps);
    chartHistoryService.init();
  }

  @Test
  @DisplayName("VideoPoint 추가 테스트")
  void addVideoPointTest() {
    // when
    chartHistoryService.addVideoPoint(TEST_VIDEO_ID_V1, TEST_VIDEO_POINT_V1);

    // then
    verify(zSetOps).add(eq(ChartHistoryService.VIDEO_POINT), eq(String.valueOf(TEST_VIDEO_ID_V1)),
        eq(TEST_VIDEO_POINT_V1));
  }

  @Test
  @DisplayName("탑 비디오 요청 테스트")
  void getTopVideosTest() {
    // given
    Set<ZSetOperations.TypedTuple<String>> typedTuples = Set.of(
        new DefaultTypedTuple<>(String.valueOf(TEST_VIDEO_ID_V1), TEST_VIDEO_POINT_V1),
        new DefaultTypedTuple<>(String.valueOf(TEST_VIDEO_ID_V2), TEST_VIDEO_POINT_V2),
        new DefaultTypedTuple<>(String.valueOf(TEST_VIDEO_ID_V3), TEST_VIDEO_POINT_V3)
    );
    given(zSetOps.reverseRangeWithScores(anyString(), anyLong(), anyLong())).willReturn(
        typedTuples);

    // when
    var topVideos = chartHistoryService.getTopVideos();

    // then
    assertThat(topVideos).hasSize(3);
  }

  @Test
  @DisplayName("VideoPoint 삭제 테스트")
  void deleteVideoPointTest() {
    // when
    chartHistoryService.deleteVideoPoint();

    // then
    verify(redisTemplate).delete(ChartHistoryService.VIDEO_POINT);
  }

}
