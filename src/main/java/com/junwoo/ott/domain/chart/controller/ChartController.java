package com.junwoo.ott.domain.chart.controller;

import com.junwoo.ott.domain.chart.dto.response.VideoPointResponseDto;
import com.junwoo.ott.domain.chart.service.ChartService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1")
@RestController
public class ChartController {

  private final ChartService chartService;

  @GetMapping("/chart")
  public List<VideoPointResponseDto> getChart() {
    return chartService.getChart();
  }

}
