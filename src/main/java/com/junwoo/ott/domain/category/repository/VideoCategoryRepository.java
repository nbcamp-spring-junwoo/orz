package com.junwoo.ott.domain.category.repository;

import com.junwoo.ott.domain.category.entity.VideoCategory;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import java.util.Set;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VideoCategoryRepository extends JpaRepository<VideoCategory, Long> {

  void deleteAllByVideo(Video video);

  Page<VideoCategory> findByCategoryTypeAndGenresIn(CategoryType categoryType, Set<GenreType> genres, Pageable pageable);

}
