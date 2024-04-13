package com.junwoo.ott.domain.video.controller;

import com.junwoo.ott.domain.video.dto.body.VideoCreateDto;
import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.domain.video.service.VideoAdminService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("api/v1/admin/videos")
@RestController
public class VideoAdminController {

  private final VideoAdminService videoAdminService;

  @PostMapping
  public void postVideo(@Validated @RequestBody final VideoCreateDto videoCreateDto) {
    videoAdminService.postVideo(new VideoCreateRequestDto(videoCreateDto));
  }

  @GetMapping("/{videoId}")
  public void getVideo(@PathVariable final Long videoId) {
  }
}
