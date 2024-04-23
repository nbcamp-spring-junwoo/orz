package com.junwoo.ott.domain.subscription.repository;

import com.junwoo.ott.domain.subscription.entity.Subscription;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

  Optional<Subscription> findByUser_UserIdAndCard_CardIdAndMembership_MembershipId(
      Long userId,
      Long cardId,
      Long membershipId
  );

}
