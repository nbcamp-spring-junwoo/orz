package com.junwoo.ott.domain.authority.repository;

import com.junwoo.ott.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AuthorityRepository extends JpaRepository<User, Long> {

}
