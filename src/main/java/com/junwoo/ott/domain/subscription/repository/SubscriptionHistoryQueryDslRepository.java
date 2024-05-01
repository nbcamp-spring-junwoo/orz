package com.junwoo.ott.domain.subscription.repository;

public interface SubscriptionHistoryQueryDslRepository {

  boolean existsActiveSubscription(Long userId);

}
