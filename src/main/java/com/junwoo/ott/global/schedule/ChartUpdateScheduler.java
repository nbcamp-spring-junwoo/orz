package com.junwoo.ott.global.schedule;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecutionException;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChartUpdateScheduler {

  private final JobLauncher jobLauncher;
  private final ApplicationContext applicationContext;

  @Scheduled(cron = "0 0 * * * *")  // 정각마다 실행
  @SchedulerLock(name = "updateChartJob")
  public void updateChartJobRun() throws JobExecutionException {
    Job job = applicationContext.getBean("updateChartJob", Job.class);
    JobParameters jobParameters = new JobParametersBuilder()
        .addLong("time", System.currentTimeMillis())
        .toJobParameters();

    jobLauncher.run(job, jobParameters);
  }

}
