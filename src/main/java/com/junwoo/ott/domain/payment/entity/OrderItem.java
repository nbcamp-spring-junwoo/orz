package com.junwoo.ott.domain.payment.entity;

import com.junwoo.ott.domain.coupon.entity.CouponIssuance;
import com.junwoo.ott.domain.payment.dto.response.OrderItemResponseDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class OrderItem {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long orderItemId;
  @Column(nullable = false)
  private String itemCode;
  @Column(nullable = false)
  private Integer basePrice;
  @Column(nullable = false)
  private Integer discountingPrice = 0;
  @Column(nullable = false)
  private Integer discountedPrice;

  @Builder
  public OrderItem(
      final String itemCode,
      final Integer basePrice,
      final Integer discountingPrice,
      final Order order,
      final CouponIssuance couponIssuance
  ) {
    this.itemCode = itemCode;
    this.basePrice = basePrice;
    this.discountingPrice = discountingPrice;
    this.order = order;
    this.couponIssuance = couponIssuance;
    this.discountedPrice = basePrice - discountingPrice;
  }


  @ManyToOne(optional = false)
  @JoinColumn(name = "order_id")
  private Order order;

  @OneToOne
  @JoinColumn(name = "coupon_issuance_id")
  private CouponIssuance couponIssuance;

  public void setParents(final Order order) {
    this.order = order;
    order.addOrderItem(this);
  }

  public OrderItemResponseDto toResponseDto() {
    return OrderItemResponseDto.builder()
        .orderItemId(orderItemId)
        .itemCode(itemCode)
        .totalPrice(basePrice)
        .discountingPrice(discountingPrice)
        .discountedPrice(discountedPrice)
        .build();
  }

}
