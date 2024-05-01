package com.junwoo.ott.domain.membership.repository;

import com.junwoo.ott.domain.membership.entity.Membership;
import com.junwoo.ott.global.customenum.MembershipType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

  Optional<Membership> findByMembershipType(MembershipType membershipType);

}
