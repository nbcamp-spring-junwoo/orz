package com.junwoo.ott.domain.auth.repository;

import com.junwoo.ott.domain.auth.entity.Oauth;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OauthRepository extends JpaRepository<Oauth, String> {

}
