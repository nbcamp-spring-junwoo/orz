package com.junwoo.ott.domain.coupon.repository;

import com.junwoo.ott.domain.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
