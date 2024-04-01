package com.junwoo.ott.domain.coupon.repository;

import com.junwoo.ott.domain.coupon.entity.Coupon;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

  Page<Coupon> findAllByOrderByStartAtDesc(final Pageable pageable);

}
