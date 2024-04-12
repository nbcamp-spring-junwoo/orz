package com.junwoo.ott.domain.payment.repository;

import com.junwoo.ott.domain.payment.entity.Card;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Long> {

  List<Card> findAllByUser_UserId(final Long userId);

  boolean existsByNumber(String cardNumber);

}
