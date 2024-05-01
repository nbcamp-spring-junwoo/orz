package com.junwoo.ott.global.batch;

import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.global.batch.reader.CouponIssuanceReader;
import com.junwoo.ott.global.batch.writer.CouponIssuanceWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.support.DefaultBatchConfiguration;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Slf4j(topic = "CouponIssuanceUpdateJob")
@RequiredArgsConstructor
@Configuration
public class CouponIssuanceUpdateJob extends DefaultBatchConfiguration {

  private final CouponIssuanceReader couponIssuanceReader;
  private final CouponIssuanceWriter couponIssuanceWriter;

  public static final int CHUNK_SIZE = 50;

  @Bean
  public Job couponIssuanceJob(final JobRepository jobRepository, final Step couponIssuanceStep) {

    return new JobBuilder("couponIssuanceJob", jobRepository)
        .start(couponIssuanceStep)
        .build();
  }

  @Bean
  public Step couponIssuanceStep(final JobRepository jobRepository,
      final PlatformTransactionManager transactionManager) throws Exception {

    return new StepBuilder("couponIssuanceStep", jobRepository)
        .<CouponIssuance, CouponIssuance>chunk(CHUNK_SIZE, transactionManager)
        .reader(couponIssuanceReader)
        .writer(couponIssuanceWriter)
        .build();
  }

}
