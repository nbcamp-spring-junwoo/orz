package com.junwoo.ott.domain.video.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class VideoReadRequestDto {

    private Long videoId;
    private String title;
    private Pageable pageable;

    public VideoReadRequestDto(Pageable pageable) {
        this.pageable = pageable;
    }

    public VideoReadRequestDto(String title, Pageable pageable) {
        this.title = title;
        this.pageable = pageable;
    }

}
