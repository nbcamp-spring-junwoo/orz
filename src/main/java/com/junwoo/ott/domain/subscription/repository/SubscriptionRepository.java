package com.junwoo.ott.domain.subscription.repository;

import com.junwoo.ott.domain.subscription.entity.Subscription;
import com.junwoo.ott.global.customenum.MembershipType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SubscriptionRepository extends JpaRepository<Subscription, Long> {

  Optional<Subscription> findByUser_UserIdAndCard_CardIdAndMembership_MembershipType(
      Long userId,
      Long cardId,
      MembershipType membershipType
  );

  Optional<Subscription> findByUser_UserIdAndCard_CardIdAndMembership_MembershipId(
      Long userId,
      Long cardId,
      Long membershipId
  );

}
