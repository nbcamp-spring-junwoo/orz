package com.junwoo.ott.domain.admin.repository;

import com.junwoo.ott.domain.user.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<User, Long> {

}
