package com.junwoo.ott.domain.membership.repository;

import com.junwoo.ott.domain.membership.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

}
