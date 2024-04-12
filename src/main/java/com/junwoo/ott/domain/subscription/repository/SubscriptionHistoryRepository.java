package com.junwoo.ott.domain.subscription.repository;

import com.junwoo.ott.domain.subscription.entity.SubscriptionHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionHistoryRepository extends JpaRepository<SubscriptionHistory, Long>,
    SubscriptionHistoryQueryDslRepository {

}
