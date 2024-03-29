package com.junwoo.ott.domain.video.controller;

import com.junwoo.ott.domain.video.dto.body.VideoCreateDto;
import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.domain.video.service.VideoService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
@RestController
public class VideoController {

  private final VideoService videoService;

  @PostMapping
  public void postVideo(@Validated @RequestBody VideoCreateDto dto) {
    VideoCreateRequestDto createDto = new VideoCreateRequestDto(dto);
    videoService.createVideo(createDto);
  }

}