package com.junwoo.ott.domain.coupon.controller;

import com.junwoo.ott.domain.coupon.dto.request.CouponIssuanceCreateRequestDto;
import com.junwoo.ott.domain.coupon.dto.request.CouponReadRequestDto;
import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.service.CouponLockService;
import com.junwoo.ott.domain.coupon.service.CouponService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class CouponController {

  private final CouponService couponService;
  private final CouponLockService couponLockService;

  private final JobLauncher jobLauncher;
  private final ApplicationContext applicationContext;

  @GetMapping("users/me/coupons")
  public ResponseDto<Page<CouponIssuanceReadResponseDto>> getCoupons(
      final @AuthenticationPrincipal UserDetailsImpl userDetails,
      final Pageable pageable
  ) {
    CouponReadRequestDto dto = new CouponReadRequestDto(userDetails.getUserId(), pageable);
    Page<CouponIssuanceReadResponseDto> result = couponService.getCoupons(dto);

    return ResponseDto.ok(result);
  }

  @PostMapping("/coupons/get-coupon/{couponId}")
  public void postCouponIssuance(
      final @PathVariable("couponId") Long couponId,
      final @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    CouponIssuanceCreateRequestDto createRequestDto = new CouponIssuanceCreateRequestDto(
        couponId, userDetails.getUserId());
    couponLockService.createCouponIssuanceLock(createRequestDto);
  }

  @GetMapping("/launch")
  public void launcher() throws Exception {
    Job job = applicationContext.getBean("couponIssuanceJob", Job.class);

    jobLauncher.run(job, new JobParametersBuilder()
        .addString("couponIssuanceJob", String.valueOf(System.currentTimeMillis()))
        .toJobParameters());
  }

}
