package com.junwoo.ott.domain.video.dto.response;

import com.junwoo.ott.domain.category.dto.body.CategoryInfoDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.global.customenum.RatingType;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoReadResponseDto {

    private final Long videoId;
    private final String title;
    private final String description;
    private final RatingType ratingType;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
    private final Set<CategoryInfoDto> categories;

    public VideoReadResponseDto(final Video video, Set<CategoryInfoDto> categories) {
        this.videoId = video.getVideoId();
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.ratingType = video.getRatingType();
        this.createdAt = video.getCreatedAt();
        this.updatedAt = video.getUpdatedAt();
        this.categories = categories;
    }

}
