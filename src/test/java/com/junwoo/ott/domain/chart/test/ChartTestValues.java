package com.junwoo.ott.domain.chart.test;

import com.junwoo.ott.domain.video.entity.Video;

public interface ChartTestValues {

  Long TEST_VIDEO_ID_V1 = 1L;
  Long TEST_VIDEO_ID_V2 = 2L;
  Long TEST_VIDEO_ID_V3 = 3L;
  Double TEST_VIDEO_POINT_V1 = 100.0;
  Double TEST_VIDEO_POINT_V2 = 50.0;
  Double TEST_VIDEO_POINT_V3 = 10.0;
  String TEST_VIDEO_TITLE_V1 = "테스트 비디오 1";
  String TEST_VIDEO_TITLE_V2 = "테스트 비디오 2";
  String TEST_VIDEO_TITLE_V3 = "테스트 비디오 3";
  Video TEST_VIDEO_V1 = Video.builder()
      .videoId(TEST_VIDEO_ID_V1)
      .title(TEST_VIDEO_TITLE_V1)
      .build();
  Video TEST_VIDEO_V2 = Video.builder()
      .videoId(TEST_VIDEO_ID_V2)
      .title(TEST_VIDEO_TITLE_V2)
      .build();
  Video TEST_VIDEO_V3 = Video.builder()
      .videoId(TEST_VIDEO_ID_V3)
      .title(TEST_VIDEO_TITLE_V3)
      .build();

}
