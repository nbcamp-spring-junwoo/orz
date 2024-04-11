package com.junwoo.ott.domain.category.service;

import com.junwoo.ott.domain.category.entity.Category;
import com.junwoo.ott.domain.category.entity.VideoCategory;
import com.junwoo.ott.domain.category.repository.VideoCategoryRepository;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class VideoCategoryService {

    private final VideoCategoryRepository videoCategoryRepository;

    public void associateVideoWithCategory(Video video, Category category) {
        VideoCategory videoCategory = new VideoCategory(video, category);
        videoCategoryRepository.save(videoCategory);
    }

    public Page<VideoCategory> findVideosByCategory(CategoryType categoryType, Set<GenreType> genres, Pageable pageable) {
        Page<VideoCategory> allVideoCategories = videoCategoryRepository.findByCategoryType(categoryType, pageable);

        List<VideoCategory> filteredVideoCategories = allVideoCategories.stream()
            .filter(vc -> vc.getCategory().getGenres().stream().anyMatch(genres::contains))
            .collect(Collectors.toList());

        return new PageImpl<>(filteredVideoCategories, pageable, filteredVideoCategories.size());
    }

    public void removeCategoriesByVideo(Video video) {
        videoCategoryRepository.deleteAllByVideo(video);
    }

}
