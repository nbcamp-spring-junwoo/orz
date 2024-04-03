package com.junwoo.ott.domain.episode.controller;

import com.junwoo.ott.domain.episode.dto.body.EpisodeCreateDto;
import com.junwoo.ott.domain.episode.dto.body.EpisodeUpdateDto;
import com.junwoo.ott.domain.episode.dto.request.EpisodeCreateRequestDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeReadResponseDto;
import com.junwoo.ott.domain.episode.service.EpisodeService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/videos/{videoId}/episodes")
@RestController
public class EpisodeController {

    private final EpisodeService episodeService;

    @PostMapping
    public void postEpisode(
        final @PathVariable Long videoId,
        final @Validated @RequestBody EpisodeCreateDto dto
    ) {
        EpisodeCreateRequestDto createDto = new EpisodeCreateRequestDto(videoId, dto.getTitle(), dto.getReleasedAt());
        episodeService.createEpisode(createDto);
    }

    @GetMapping
    public ResponseDto<Page<EpisodeReadResponseDto>> getEpisodes(
        final @PathVariable Long videoId,
        final Pageable pageable
    ) {
        Page<EpisodeReadResponseDto> episodesPage = episodeService.getEpisodesByVideo(videoId, pageable);
        return ResponseDto.ok(episodesPage);
    }

    @GetMapping("/{episodeId}")
    public ResponseDto<EpisodeReadResponseDto> getEpisode(
        final @PathVariable Long videoId,
        final @PathVariable Long episodeId
    ) {
        EpisodeReadResponseDto episodeReadResponseDto = episodeService.getEpisodeByVideo(videoId, episodeId);
        return ResponseDto.ok(episodeReadResponseDto);
    }

    @PutMapping("/{episodeId}")
    public void putEpisode(
        final @PathVariable Long videoId,
        final @PathVariable Long episodeId,
        final @RequestBody EpisodeUpdateDto dto
    ) {
        episodeService.updateEpisode(videoId, episodeId, dto);
    }

}
