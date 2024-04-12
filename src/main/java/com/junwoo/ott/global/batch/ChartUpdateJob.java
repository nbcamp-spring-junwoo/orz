package com.junwoo.ott.global.batch;

import com.junwoo.ott.domain.chart.service.ChartService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j(topic = "ChartUpdateJob")
@Configuration
@RequiredArgsConstructor
public class ChartUpdateJob extends DefaultBatchConfiguration {

  private final ChartService chartService;

  @Bean
  public Job updateChartJob(final JobRepository jobRepository, final Step updateChartStep) {
    return new JobBuilder("updateChartJob", jobRepository)
        .start(updateChartStep)
        .build();
  }

  @Bean
  public Step updateChartStep(final JobRepository jobRepository) {
    return new StepBuilder("updateChartStep", jobRepository)
        .tasklet((contribution, chunkContext) -> {
          log.info("updateChartStep 실행");
          chartService.updateChart();

          return RepeatStatus.FINISHED;
        }, getTransactionManager())
        .build();
  }

}
