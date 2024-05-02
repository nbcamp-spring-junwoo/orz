package com.junwoo.ott.domain.like.repository;

import com.junwoo.ott.domain.like.entity.Like;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeRepository extends JpaRepository<Like, Long> {

  boolean existsLikeByUser_UserIdAndVideo_VideoId(final Long userId, final Long videoId);

  Optional<Like> findLikeByUser_UserIdAndVideo_VideoId(final Long userId, final Long videoId);

  Page<Like> findLikeByUser_UserIdOrderByRegisteredAtDesc(final Long userId,
      final Pageable pageable);

}

