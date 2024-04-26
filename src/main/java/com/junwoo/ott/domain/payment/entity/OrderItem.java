package com.junwoo.ott.domain.payment.entity;

import com.junwoo.ott.domain.coupon.dto.response.CouponIssuanceReadResponseDto;
import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.global.customenum.payment.OrderType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderItemId;
  @Column(nullable = false, length = 50)
  private String name;
  @Column(nullable = false)
  private String itemCode;
  @Column(nullable = false)
  private Integer basePrice;
  @Column(nullable = false)
  private Integer discountingPrice = 0;
  @Column(nullable = false)
  private Integer discountedPrice;

  @PrePersist
  public void prePersist() {
    this.discountedPrice = this.basePrice - this.discountingPrice;
  }

  @ManyToOne(optional = false)
  @JoinColumn(name = "order_id")
  private Order order;

  @OneToOne
  @JoinColumn(name = "coupon_issuance_id")
  private CouponIssuance couponIssuance;

  public static OrderItem ofSubscription(
      final Subscription subscription, final CouponIssuanceReadResponseDto couponIssuance
  ) {
    Integer totalPrice = subscription.getMembership().getPrice();

    return OrderItem.builder()
        .name(subscription.getMembership().getMembershipType().name().substring(5) + " 구독")
        .itemCode(getItemCode(subscription))
        .basePrice(totalPrice)
        .discountingPrice(calculateDiscountingPrice(totalPrice, couponIssuance))
        .build();
  }

  private static String getItemCode(final Subscription subscription) {
    return OrderType.SUBSCRIPTION.getCode() + subscription.getMembership().getMembershipId();
  }

  private static Integer calculateDiscountingPrice(
      final Integer totalPrice, final CouponIssuanceReadResponseDto couponIssuance
  ) {
    if (couponIssuance == null) {
      return 0;
    }

    return switch (couponIssuance.getCouponType()) {
      case FIX -> Math.min(couponIssuance.getDiscount(), totalPrice);
      case RATIO -> totalPrice * couponIssuance.getDiscount() / 100;
    };
  }

  public void setParents(final Order order) {
    this.order = order;
    order.addOrderItem(this);
  }

}
