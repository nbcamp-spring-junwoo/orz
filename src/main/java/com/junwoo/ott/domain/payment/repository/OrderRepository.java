package com.junwoo.ott.domain.payment.repository;

import com.junwoo.ott.domain.payment.entity.Order;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

  Optional<Order> findByOrderId(String orderId);

}
