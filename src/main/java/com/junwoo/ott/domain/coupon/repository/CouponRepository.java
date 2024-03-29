package com.junwoo.ott.domain.coupon.repository;

import com.junwoo.ott.domain.coupon.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

  // 시작일을 기준으로 내림차순 정렬
  @Query("select c from Coupon c order by c.startAt desc")
  Page<Coupon> getCoupons(Pageable pageable);
}
