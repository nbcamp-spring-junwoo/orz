package com.junwoo.ott.global.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChartUpdateScheduler {

  private final JobLauncher jobLauncher;
  private final Job updateChartJob;

  @Scheduled(cron = "0 0 * * * *")  // 정각마다 실행
  public void updateChartJobRun() throws JobExecutionException {
    JobParameters parameters = new JobParametersBuilder()
        .addString("updateChart", "exchangeJob" + System.currentTimeMillis())
        .toJobParameters();

    jobLauncher.run(updateChartJob, parameters);
  }

}
