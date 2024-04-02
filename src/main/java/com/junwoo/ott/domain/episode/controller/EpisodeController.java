package com.junwoo.ott.domain.episode.controller;

import com.junwoo.ott.domain.episode.dto.body.EpisodeCreateDto;
import com.junwoo.ott.domain.episode.dto.request.EpisodeCreateRequestDto;
import com.junwoo.ott.domain.episode.service.EpisodeService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/videos/{videoId}/episodes")
@RestController
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping
    public void postEpisode(@PathVariable Long videoId, @Validated @RequestBody EpisodeCreateDto dto) {
        EpisodeCreateRequestDto createDto = new EpisodeCreateRequestDto(dto.getTitle(), dto.getReleasedAt());
        episodeService.createEpisode(videoId, createDto);
    }

}
