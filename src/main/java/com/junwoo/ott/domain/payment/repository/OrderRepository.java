package com.junwoo.ott.domain.payment.repository;

import com.junwoo.ott.domain.payment.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, String> {

}
