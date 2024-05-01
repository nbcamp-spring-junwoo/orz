package com.junwoo.ott.domain.payment.repository;

import com.junwoo.ott.domain.payment.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {

}
