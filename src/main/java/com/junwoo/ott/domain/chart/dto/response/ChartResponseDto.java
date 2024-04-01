package com.junwoo.ott.domain.chart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ChartResponseDto {

  private final Long videoId;
  private final String videoTitle;

}
