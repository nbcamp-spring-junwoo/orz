package com.junwoo.ott.domain.episode.controller;

import com.junwoo.ott.domain.episode.dto.body.EpisodeCreateDto;
import com.junwoo.ott.domain.episode.dto.body.EpisodeUpdateDto;
import com.junwoo.ott.domain.episode.dto.request.EpisodeCreateRequestDto;
import com.junwoo.ott.domain.episode.dto.request.EpisodeReadRequestDto;
import com.junwoo.ott.domain.episode.dto.request.EpisodeUpdateRequestDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeCreateResponseDto;
import com.junwoo.ott.domain.episode.dto.response.EpisodeReadResponseDto;
import com.junwoo.ott.domain.episode.service.EpisodeAccessService;
import com.junwoo.ott.domain.episode.service.EpisodeService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/videos")
@RestController
public class EpisodeController {

    private final EpisodeService episodeService;
    private final EpisodeAccessService episodeAccessService;

    @Secured(value = "ROLE_ADMIN")
    @PostMapping("{videoId}/episodes")
    public EpisodeCreateResponseDto postEpisode(
        final @PathVariable Long videoId,
        final @Validated @RequestBody EpisodeCreateDto dto
    ) {
        // Pass videoId to the service method
        EpisodeCreateRequestDto createDto = new EpisodeCreateRequestDto(dto, videoId);
        return episodeService.createEpisode(createDto);
    }

    @GetMapping("{videoId}/episodes")
    public ResponseDto<Page<EpisodeReadResponseDto>> getEpisodes(
        @PathVariable Long videoId,
        Pageable pageable
    ) {
        EpisodeReadRequestDto requestDto = new EpisodeReadRequestDto(videoId, pageable);
        Page<EpisodeReadResponseDto> episodesPages = episodeService.getEpisodesByVideo(requestDto);
        return ResponseDto.ok(episodesPages);
    }

    @GetMapping("{videoId}/episodes/{episodeId}")
    public ResponseDto<EpisodeReadResponseDto> getEpisode(
        final @PathVariable Long videoId,
        final @PathVariable Long episodeId
    ) {
        EpisodeReadRequestDto requestDto = new EpisodeReadRequestDto(videoId, episodeId);
        EpisodeReadResponseDto episodePage = episodeService.getEpisodeByVideo(requestDto);
        return ResponseDto.ok(episodePage);
    }

    @Secured(value = "ROLE_ADMIN")
    @PutMapping("{videoId}/episodes/{episodeId}")
    public void putEpisode(
        @PathVariable Long videoId,
        @PathVariable Long episodeId,
        @Validated @RequestBody EpisodeUpdateDto dto
    ) {
        EpisodeUpdateRequestDto updateRequestDto = new EpisodeUpdateRequestDto(videoId, episodeId, dto);
        episodeService.updateEpisode(updateRequestDto);
    }

    @Secured(value = "ROLE_ADMIN")
    @DeleteMapping("/episodes/{episodeId}")
    public void deleteEpisode(@PathVariable("episodeId") Long episodeId) {
        episodeService.deleteEpisode(episodeId);
    }

    @GetMapping("/{videoId}/episodes/{episodeId}/watch")
    public ResponseEntity<Object> watchEpisode(
        @PathVariable Long videoId,
        @PathVariable Long episodeId,
        @AuthenticationPrincipal UserDetailsImpl userDetails) {

        boolean canAccess = episodeAccessService.canUserAccessEpisode(userDetails, videoId, episodeId);

        if (!canAccess) {
            return ResponseEntity
                .status(HttpStatus.FORBIDDEN)
                .body("이 에피소드를 볼려면 맴버쉽 등급을 높이세요.");
        }

        String videoLink = episodeAccessService.getEpisodeById(episodeId).getVideoLink();
        return ResponseEntity.ok().body(Map.of("videoLink", videoLink));
    }

}
