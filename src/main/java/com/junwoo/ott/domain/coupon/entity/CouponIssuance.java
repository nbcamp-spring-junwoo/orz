package com.junwoo.ott.domain.coupon.entity;

import com.junwoo.ott.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;
import org.springframework.data.annotation.CreatedDate;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update coupon_issuance set used_at = NOW() where coupon_issuance_id = ?")
@SQLRestriction(value = "used_at is NULL")
@Entity
public class CouponIssuance {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long couponIssuanceId;
  @ManyToOne
  @JoinColumn(name = "coupon_id")
  private Coupon coupon;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  @CreatedDate
  @Column(updatable = false)
  private LocalDateTime issuedAt;
  private LocalDateTime usedAt;

  public static CouponIssuance of(Coupon coupon, User user) {

    return CouponIssuance.builder()
        .coupon(coupon)
        .user(user)
        .build();
  }

}
