package com.junwoo.ott.domain.coupon.repository;

import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CouponIssuanceRepository extends JpaRepository<CouponIssuance, Long> {

  @Query("select c from CouponIssuance c where c.usedAt is null and c.user.userId = :userId order by c.issuedAt desc")
  Page<CouponIssuance> getCouponIssuance(Long userId, Pageable pageable);

}
