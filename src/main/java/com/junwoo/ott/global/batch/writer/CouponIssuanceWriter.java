package com.junwoo.ott.global.batch.writer;

import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.domain.coupon.repository.CouponIssuanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class CouponIssuanceWriter implements ItemWriter<CouponIssuance> {

  private final CouponIssuanceRepository couponIssuanceRepository;

  @Override
  public void write(final Chunk<? extends CouponIssuance> chunk) throws Exception {
    couponIssuanceRepository.deleteAll(chunk.getItems());
  }

}
