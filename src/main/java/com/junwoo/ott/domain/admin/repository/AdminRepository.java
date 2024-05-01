package com.junwoo.ott.domain.admin.repository;

import com.junwoo.ott.domain.admin.entity.Admin;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin, Long> {

  boolean existsByUsername(String username);

  Optional<Admin> findByUsername(String username);

}
