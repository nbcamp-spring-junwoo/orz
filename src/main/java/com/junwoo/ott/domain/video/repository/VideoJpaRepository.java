package com.junwoo.ott.domain.video.repository;

import com.junwoo.ott.domain.video.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoJpaRepository extends JpaRepository<Video, Long> {

}
