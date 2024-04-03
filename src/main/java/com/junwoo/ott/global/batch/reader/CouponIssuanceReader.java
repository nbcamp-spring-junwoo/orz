package com.junwoo.ott.global.batch.reader;

import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.domain.coupon.repository.CouponIssuanceRepository;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CouponIssuanceReader implements ItemReader<CouponIssuance> {

  private final CouponIssuanceRepository couponIssuanceRepository;

  private int currentIndex = 0;
  private final LocalDate localDate = LocalDate.now();

  @Override
  public CouponIssuance read()
      throws Exception {

    return (currentIndex < couponIssuanceRepository.getCouponIssuance(localDate).size())
        ? couponIssuanceRepository.getCouponIssuance(localDate).get(currentIndex++) : null;
  }

}
