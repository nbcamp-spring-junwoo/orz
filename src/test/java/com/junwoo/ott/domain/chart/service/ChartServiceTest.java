package com.junwoo.ott.domain.chart.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

import com.junwoo.ott.domain.chart.dto.response.VideoPointResponseDto;
import com.junwoo.ott.domain.chart.entity.Chart;
import com.junwoo.ott.domain.chart.repository.ChartJpaRepository;
import com.junwoo.ott.domain.chart.test.ChartTestValues;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.service.VideoService;
import java.util.List;
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
  void 차트_업데이트() {
    // given
    List<VideoPointResponseDto> videoPointResponseDto = List.of(
        new VideoPointResponseDto(1L, 100.0),
        new VideoPointResponseDto(2L, 50.0),
        new VideoPointResponseDto(3L, 10.0)
    );
    given(chartHistoryService.getTopVideos()).willReturn(videoPointResponseDto);

    List<Video> videos = List.of(
        TEST_VIDEO_V1,
        TEST_VIDEO_V2,
        TEST_VIDEO_V3
    );
    given(videoService.getByVideoIdIn(any())).willReturn(videos);

    // when
    chartService.updateChart();

    // then
    verify(chartJpaRepository).saveAll(argThat((List<Chart> charts) ->
        charts.size() == videos.size() && charts.stream().allMatch(chart ->
            videoPointResponseDto.stream().anyMatch(video ->
                video.getVideoId().equals(chart.getVideo().getVideoId()) &&
                    video.getPoint().equals(chart.getPoint())
            )
        )));
  }

}
