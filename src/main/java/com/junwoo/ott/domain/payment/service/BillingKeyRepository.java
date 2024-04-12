package com.junwoo.ott.domain.payment.service;

import com.junwoo.ott.domain.payment.entity.BillingKey;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BillingKeyRepository extends JpaRepository<BillingKey, Long> {

  Optional<BillingKey> findByUser_UserIdAndCard_CardId(Long userId, Long cardId);

}
