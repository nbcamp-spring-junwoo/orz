package com.junwoo.ott.domain.chart.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ChartResponseDto {

  private Long videoId;
  private String videoTitle;
  private String videoDescription;
  // private LocalDate released_at;
  private String poster_url;

}
