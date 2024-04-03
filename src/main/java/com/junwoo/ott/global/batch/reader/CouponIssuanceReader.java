package com.junwoo.ott.global.batch.reader;

import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.domain.coupon.repository.CouponIssuanceRepository;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CouponIssuanceReader implements ItemReader<CouponIssuance> {

  private final CouponIssuanceRepository couponIssuanceRepository;

  private int currentIndex = 0;
  private LocalDateTime localDate = LocalDateTime.now();

  @Override
  public CouponIssuance read()
      throws Exception {
    if (localDate.isBefore(LocalDateTime.now().minusMinutes(2))) {
      currentIndex = 0;
      localDate = LocalDateTime.now();
    }

    return (currentIndex < couponIssuanceRepository.getCouponIssuance(LocalDate.from(localDate))
        .size())
        ? couponIssuanceRepository.getCouponIssuance(LocalDate.from(localDate)).get(currentIndex++)
        : null;
  }

}
