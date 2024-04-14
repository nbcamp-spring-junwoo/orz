package com.junwoo.ott.domain.genre.repository;

import com.junwoo.ott.domain.genre.entity.VideoGenre;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoGenreRepository extends JpaRepository<VideoGenre, Long> {

  List<VideoGenre> findAllByVideoId(Long videoId);

}
