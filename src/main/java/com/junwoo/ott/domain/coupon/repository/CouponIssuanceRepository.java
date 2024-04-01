package com.junwoo.ott.domain.coupon.repository;

import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.domain.coupon.repository.queryDsl.CouponIssuanceQueryDsl;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponIssuanceRepository extends JpaRepository<CouponIssuance, Long>,
    CouponIssuanceQueryDsl {

}
