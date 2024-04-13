package com.junwoo.ott.domain.video.controller;

import com.junwoo.ott.domain.video.dto.body.VideoCreateDto;
import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoResponseDto;
import com.junwoo.ott.domain.video.service.VideoAdminService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
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

  @GetMapping
  public ResponseDto<Page<VideoResponseDto>> getVideos(
      final @PageableDefault(sort = "releasedAt", direction = Direction.DESC) Pageable pageable
  ) {
    return ResponseDto.ok(videoAdminService.getVideos(new VideoRequestDto(pageable)));
  }
}
