package com.junwoo.ott.domain.chart.repository;

import com.junwoo.ott.domain.chart.dto.response.ChartResponseDto;
import java.util.List;

public interface ChartQueryRepository {

  List<ChartResponseDto> getAllChartsWithVideoInfo();

}
