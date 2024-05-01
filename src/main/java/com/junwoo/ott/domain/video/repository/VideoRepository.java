package com.junwoo.ott.domain.video.repository;

import com.junwoo.ott.domain.video.entity.Video;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoRepository extends JpaRepository<Video, Long>, VideoQueryDslRepository {

  List<Video> findAllByVideoIdIn(List<Long> videoIds);
}
