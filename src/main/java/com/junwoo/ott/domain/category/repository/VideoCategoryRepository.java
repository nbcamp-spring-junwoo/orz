package com.junwoo.ott.domain.category.repository;

import com.junwoo.ott.domain.category.entity.VideoCategory;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.global.customenum.CategoryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface VideoCategoryRepository extends JpaRepository<VideoCategory, Long> {

  void deleteAllByVideo(Video video);

  @Query("SELECT vc FROM VideoCategory vc WHERE vc.category.type = :categoryType")
  Page<VideoCategory> findByCategoryType(@Param("categoryType") CategoryType categoryType, Pageable pageable);

}
