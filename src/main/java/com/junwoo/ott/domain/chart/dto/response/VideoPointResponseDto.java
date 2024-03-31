package com.junwoo.ott.domain.chart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoPointResponseDto {

  private final Long videoId;
  private final Double point;

}
