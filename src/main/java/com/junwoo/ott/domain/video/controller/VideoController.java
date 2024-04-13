package com.junwoo.ott.domain.video.controller;

import com.junwoo.ott.domain.video.dto.body.VideoCreateDto;
import com.junwoo.ott.domain.video.dto.body.VideoUpdateDto;
import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoReadRequestDto;
import com.junwoo.ott.domain.video.dto.request.VideoUpdateRequestDto;
import com.junwoo.ott.domain.video.dto.response.VideoReadResponseDto;
import com.junwoo.ott.domain.video.dto.response.VideoUpdateResponseDto;
import com.junwoo.ott.domain.video.service.VideoService;
import com.junwoo.ott.global.aop.VideoPoint;
import com.junwoo.ott.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/videos")
@RequiredArgsConstructor
@RestController
public class VideoController {

  private final VideoService videoService;

  @PostMapping
  public void postVideo(final @Validated @RequestBody VideoCreateDto dto) {
    VideoCreateRequestDto createDto = new VideoCreateRequestDto(dto);
    videoService.createVideo(createDto);
  }

  @GetMapping
  public ResponseDto<Page<VideoReadResponseDto>> getVideos(final Pageable pageable) {
    VideoReadRequestDto dto = new VideoReadRequestDto(pageable);
    Page<VideoReadResponseDto> videosPage = videoService.getVideos(dto);

    return ResponseDto.ok(videosPage);
  }

  @VideoPoint(points = 1.0)
  @GetMapping("{videoId}")
  public ResponseDto<VideoReadResponseDto> getVideo(final @PathVariable("videoId") Long videoId) {
    VideoReadResponseDto videoReadResponseDto = videoService.getVideo(videoId);
    return ResponseDto.ok(videoReadResponseDto);
  }

  @GetMapping("/search")
  public ResponseDto<Page<VideoReadResponseDto>> searchVideos(
      final @RequestParam String title,
      final Pageable pageable
  ) {
    VideoReadRequestDto dto = new VideoReadRequestDto(title, pageable);
    Page<VideoReadResponseDto> videosPage = videoService.getVideosByTitle(dto);
    return ResponseDto.ok(videosPage);
  }

  @PutMapping("/{videoId}")
  public ResponseDto<VideoUpdateResponseDto> updateVideo(
      final @PathVariable("videoId") Long videoId,
      final @Validated @RequestBody VideoUpdateDto dto
  ) {
    VideoUpdateRequestDto updateRequestDto = new VideoUpdateRequestDto(videoId, dto);
    VideoUpdateResponseDto updatedVideo = videoService.updateVideo(updateRequestDto);
    return ResponseDto.ok(updatedVideo);
  }

  @DeleteMapping("/{videoId}")
  public void deleteVideo(final @PathVariable("videoId") Long videoId) {
    videoService.deleteVideo(videoId);
  }

}
