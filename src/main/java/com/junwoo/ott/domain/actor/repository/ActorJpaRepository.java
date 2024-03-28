package com.junwoo.ott.domain.actor.repository;

import com.junwoo.ott.domain.actor.entity.Actor;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ActorJpaRepository extends JpaRepository<Actor, Long> {

}
