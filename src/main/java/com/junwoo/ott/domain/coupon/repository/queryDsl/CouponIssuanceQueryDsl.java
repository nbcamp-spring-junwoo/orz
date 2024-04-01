package com.junwoo.ott.domain.coupon.repository.queryDsl;

import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CouponIssuanceQueryDsl {

  Page<CouponIssuance> getCouponIssuance(final Long userId, final Pageable pageable);

}
