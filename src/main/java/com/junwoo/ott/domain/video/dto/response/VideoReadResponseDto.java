package com.junwoo.ott.domain.video.dto.response;

import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.global.customenum.RatingType;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoReadResponseDto {
    private Long videoId;
    private String title;
    private String description;
    private RatingType ratingType;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public VideoReadResponseDto(Video video) {
        this.videoId = video.getVideoId();
        this.title = video.getTitle();
        this.description = video.getDescription();
        this.ratingType = video.getRatingType();
        this.createdAt = video.getCreatedAt();
        this.updatedAt = video.getUpdatedAt();
    }

}
