package com.junwoo.ott.domain.favorite.repository;

import com.junwoo.ott.domain.favorite.entity.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

}
