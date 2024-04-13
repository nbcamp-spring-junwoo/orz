package com.junwoo.ott.domain.genre.repository;

import com.junwoo.ott.domain.genre.entity.Genre;
import com.junwoo.ott.global.customenum.GenreType;
import java.util.List;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenreRepository extends JpaRepository<Genre, Long> {

  List<Genre> findAllByNameIn(Set<GenreType> genres);

}
