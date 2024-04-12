package com.junwoo.ott.global.schedule;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CouponIssuanceScheduler {

  private final JobLauncher jobLauncher;
  private final ApplicationContext context;

  @Scheduled(cron = "0 0 * * *")
  public void runBatchJob() throws Exception {
    Job job = context.getBean("couponIssuanceJob", Job.class);

    JobParameters jobParameters = new JobParametersBuilder()
        .addString("couponIssuanceJob", "times " + System.currentTimeMillis())
        .toJobParameters();

    jobLauncher.run(job, jobParameters);
  }

}
