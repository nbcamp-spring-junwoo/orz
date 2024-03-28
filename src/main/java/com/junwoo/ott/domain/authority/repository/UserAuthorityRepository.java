package com.junwoo.ott.domain.authority.repository;

import com.junwoo.ott.domain.authority.entity.UserAuthority;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {

}
