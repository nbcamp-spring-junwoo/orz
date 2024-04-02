package com.junwoo.ott.domain.video.repository;

import com.junwoo.ott.domain.video.entity.Video;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface VideoCustomRepository {

    Page<Video> findByTitle(final String title, final Pageable pageable);

    Page<Video> getVideos(final Pageable pageable);

}
