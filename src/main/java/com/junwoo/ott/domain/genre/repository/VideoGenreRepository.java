package com.junwoo.ott.domain.genre.repository;

import com.junwoo.ott.domain.genre.entity.VideoGenre;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoGenreRepository extends JpaRepository<VideoGenre, Long> {

}
