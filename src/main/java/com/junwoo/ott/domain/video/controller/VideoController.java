package com.junwoo.ott.domain.video.controller;

import com.junwoo.ott.domain.video.dto.request.VideoReadRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoReadResponseDto;
import com.junwoo.ott.domain.video.dto.response.VideoResponseDto;
import com.junwoo.ott.domain.video.service.VideoService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
@RestController
public class VideoController {

  public final VideoService videoService;

  @GetMapping
  public ResponseDto<Page<VideoResponseDto>> getVideos(
      final @PageableDefault(sort = "releasedAt", direction = Direction.DESC) Pageable pageable
  ) {
    return ResponseDto.ok(videoService.getVideos(new VideoRequestDto(pageable)));
  }

  @GetMapping("/{videoId}")
  public ResponseDto<VideoReadResponseDto> getVideo(
      final @PageableDefault(sort = "releasedAt", direction = Direction.DESC) Pageable pageable,
      @PathVariable final Long videoId
  ) {
    return ResponseDto.ok(videoService.getVideo(new VideoReadRequestDto(pageable, videoId)));
  }
}
