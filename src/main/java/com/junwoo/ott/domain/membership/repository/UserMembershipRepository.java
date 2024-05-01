package com.junwoo.ott.domain.membership.repository;

import com.junwoo.ott.domain.membership.entity.UserMembership;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserMembershipRepository extends JpaRepository<UserMembership, Long> {

}
