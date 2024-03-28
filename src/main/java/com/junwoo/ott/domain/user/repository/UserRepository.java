package com.junwoo.ott.domain.user.repository;

import com.junwoo.ott.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

}
