package com.junwoo.ott.domain.category.repository;

import com.junwoo.ott.domain.category.entity.VideoCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoCategoryJpaRepository extends JpaRepository<VideoCategory, Long> {

}
