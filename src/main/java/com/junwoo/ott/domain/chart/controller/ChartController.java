package com.junwoo.ott.domain.chart.controller;

import com.junwoo.ott.domain.chart.dto.response.ChartResponseDto;
import com.junwoo.ott.domain.chart.service.ChartService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1")
@RestController
public class ChartController {

  private final ChartService chartService;
  private final JobLauncher jobLauncher;
  private final ApplicationContext applicationContext;

  @GetMapping("/chart")
  public ResponseDto<List<ChartResponseDto>> getChart() {
    return ResponseDto.ok(chartService.getChart());
  }

  // 테스트용
  @PostMapping("/chart/update")
  public void updateChart() {
    chartService.updateChart();
  }

  // 테스트용
  @GetMapping("/launchJob")
  public void launchJob() throws Exception {
    Job job = applicationContext.getBean("updateChartJob", Job.class);
    JobParameters jobParameters = new JobParametersBuilder()
        .addLong("time", System.currentTimeMillis())
        .toJobParameters();

    jobLauncher.run(job, jobParameters);
  }

}
