package com.junwoo.ott.domain.coupon.entity;

import com.junwoo.ott.domain.coupon.dto.request.CouponCreateRequestDto;
import com.junwoo.ott.global.common.entity.Timestamped;
import com.junwoo.ott.global.customenum.CouponType;
import com.junwoo.ott.global.customenum.MembershipType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update coupon set deleted_at = NOW() where coupon_id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@Entity
public class Coupon extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long couponId;
  private String description;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private CouponType couponType;
  @Enumerated(EnumType.STRING)
  private MembershipType membershipType;
  @Column(nullable = false)
  private Integer count;
  @Column(nullable = false)
  private int discount;
  @Column(nullable = false)
  private LocalDate startAt;
  @Column(nullable = false)
  private LocalDate endAt;
  private LocalDateTime deletedAt;

  public static Coupon of(CouponCreateRequestDto dto) {

    return Coupon.builder()
        .description(dto.getDescription())
        .couponType(CouponType.valueOf(dto.getType()))
        .membershipType(MembershipType.valueOf(dto.getMembershipType()))
        .count(dto.getCount())
        .discount(dto.getDiscount())
        .startAt(dto.getStartAt())
        .endAt(dto.getEndAt())
        .build();
  }

}
