package com.junwoo.ott.domain.chart.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;

import com.junwoo.ott.domain.chart.dto.response.ChartResponseDto;
import com.junwoo.ott.domain.chart.dto.response.VideoPointResponseDto;
import com.junwoo.ott.domain.chart.entity.Chart;
import com.junwoo.ott.domain.chart.repository.ChartJpaRepository;
import com.junwoo.ott.domain.chart.test.ChartTestValues;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.service.VideoService;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class ChartServiceTest implements ChartTestValues {

  @InjectMocks
  private ChartService chartService;
  @Mock
  private VideoService videoService;
  @Mock
  private ChartHistoryService chartHistoryService;
  @Mock
  private ChartJpaRepository chartJpaRepository;

  @Test
  @DisplayName("차트 조회 테스트")
  void 차트_조회() {
    // given
    List<ChartResponseDto> chartResponse = List.of(
        new ChartResponseDto(TEST_VIDEO_ID_V1, TEST_VIDEO_TITLE_V1),
        new ChartResponseDto(TEST_VIDEO_ID_V2, TEST_VIDEO_TITLE_V2),
        new ChartResponseDto(TEST_VIDEO_ID_V3, TEST_VIDEO_TITLE_V3)
    );
    given(chartJpaRepository.getAllChartsWithVideoInfo()).willReturn(chartResponse);

    // when
    List<ChartResponseDto> result = chartService.getChart();

    // then
    assertEquals(result, chartResponse);
  }

  @Test
  @DisplayName("차트 업데이트 테스트")
  void 차트_업데이트() {
    // given
    List<VideoPointResponseDto> videoPointResponseDto = List.of(
        new VideoPointResponseDto(TEST_VIDEO_ID_V1, TEST_VIDEO_POINT_V1),
        new VideoPointResponseDto(TEST_VIDEO_ID_V2, TEST_VIDEO_POINT_V2),
        new VideoPointResponseDto(TEST_VIDEO_ID_V3, TEST_VIDEO_POINT_V3)
    );
    given(chartHistoryService.getTopVideos()).willReturn(videoPointResponseDto);

    List<Video> videos = List.of(
        TEST_VIDEO_V1,
        TEST_VIDEO_V2,
        TEST_VIDEO_V3
    );
    given(videoService.getByVideoIdIn(any())).willReturn(videos);

    Map<Long, Double> videoPointMap = Map.of(
        TEST_VIDEO_ID_V1, TEST_VIDEO_POINT_V1,
        TEST_VIDEO_ID_V2, TEST_VIDEO_POINT_V2,
        TEST_VIDEO_ID_V3, TEST_VIDEO_POINT_V3
    );

    // when
    chartService.updateChart();

    // then
    then(chartJpaRepository).should().saveAll(argThat((List<Chart> charts) ->
        charts.size() == videoPointMap.size() && charts.stream().allMatch(chart ->
            videoPointMap.get(chart.getVideo().getVideoId()).equals(chart.getPoint())
        )
    ));
  }

}

