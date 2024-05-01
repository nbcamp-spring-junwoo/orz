package com.junwoo.ott.domain.user.repository;

import com.junwoo.ott.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

  boolean existsByUsername(String username);

  Optional<User> findByUsername(String username);

  boolean existsByEmail(String newEmail);

}
