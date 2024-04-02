package com.junwoo.ott.domain.payment.repository;

import com.junwoo.ott.domain.payment.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

  boolean existsByCardNumber(String cardNumber);

}
