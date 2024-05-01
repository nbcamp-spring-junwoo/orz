package com.junwoo.ott.domain.subscription.repository;

import com.junwoo.ott.domain.subscription.entity.QSubscriptionHistory;
import com.querydsl.core.types.dsl.Wildcard;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SubscriptionHistoryQueryDslRepositoryImpl implements
    SubscriptionHistoryQueryDslRepository {

  private final JPAQueryFactory queryFactory;

  @Override
  public boolean existsActiveSubscription(final Long userId) {
    QSubscriptionHistory subscriptionHistory = QSubscriptionHistory.subscriptionHistory;

    return queryFactory.select(Wildcard.countAsInt)
               .from(subscriptionHistory)
               .where(subscriptionHistory.user.userId.eq(userId)
                   .and(subscriptionHistory.cancelAt.isNull())
                   .and(subscriptionHistory.expireAt.goe(LocalDate.now())))
               .fetchFirst() > 0;
  }

}
