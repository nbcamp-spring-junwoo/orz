package com.junwoo.ott.domain.payment.entity;

import com.junwoo.ott.domain.payment.dto.response.OrderItemResponseDto;
import com.junwoo.ott.domain.payment.dto.response.OrderResponseDto;
import com.junwoo.ott.domain.payment.util.PaymentUtil;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.global.common.entity.Timestamped;
import com.junwoo.ott.global.customenum.payment.OrderType;
import jakarta.persistence.Column;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Order extends Timestamped {

  @Default
  @Id
  private String orderId = PaymentUtil.generateOrderId(OrderType.PAYMENT);

  @Column(nullable = false, length = 50)
  private String name;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @Default
  @OneToMany(mappedBy = "order")
  private List<OrderItem> orderItems = new ArrayList<>();

  protected void addOrderItem(final OrderItem orderItem) {
    this.orderItems.add(orderItem);
  }

  public OrderResponseDto toResponseDto() {
    return OrderResponseDto.builder()
        .orderId(orderId)
        .name(name)
        .orderItems(orderItems.stream().map(OrderItemResponseDto::of).toList())
        .createdAt(getCreatedAt())
        .updatedAt(getUpdatedAt())
        .build();
  }

  public void setOrderName() {
    int orderItemCount = this.orderItems.size();
    String firstOrderItemName = this.orderItems.get(0).getName();

    this.name = (orderItemCount == 1)
        ? firstOrderItemName
        : (firstOrderItemName + " 외 " + (orderItemCount - 1) + "건");
  }

}
