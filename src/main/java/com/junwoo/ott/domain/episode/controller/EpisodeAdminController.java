package com.junwoo.ott.domain.episode.controller;

import com.junwoo.ott.domain.episode.dto.body.EpisodeCreateDto;
import com.junwoo.ott.domain.episode.dto.request.EpisodeCreateRequestDto;
import com.junwoo.ott.domain.episode.service.EpisodeAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1/admin/episodes")
@RestController
public class EpisodeAdminController {

  private final EpisodeAdminService episodeAdminService;

  @PostMapping
  public void postEpisode(@Validated @RequestBody final EpisodeCreateDto episodeCreateDto) {
    episodeAdminService.postEpisode(new EpisodeCreateRequestDto(episodeCreateDto));
  }

}
