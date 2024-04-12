package com.junwoo.ott.domain.payment.entity;

import com.junwoo.ott.domain.payment.dto.response.OrderResponseDto;
import com.junwoo.ott.domain.payment.util.PaymentDataGenerator;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.common.entity.Timestamped;
import com.junwoo.ott.global.customenum.payment.OrderType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order extends Timestamped {

  @Id
  @Default
  private String orderId = PaymentDataGenerator.generateOrderId(OrderType.PAYMENT);

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @OneToMany(mappedBy = "order")
  @Default
  private List<OrderItem> orderItems = new ArrayList<>();

  public void setParents(final User user) {
    this.user = user;
  }

  protected void addOrderItem(final OrderItem orderItem) {
    this.orderItems.add(orderItem);
  }

  public OrderResponseDto toResponseDto() {
    return OrderResponseDto.builder()
        .orderId(orderId)
        .orderItems(orderItems.stream().map(OrderItem::toResponseDto).toList())
        .createdAt(getCreatedAt())
        .updatedAt(getUpdatedAt())
        .build();
  }

}
