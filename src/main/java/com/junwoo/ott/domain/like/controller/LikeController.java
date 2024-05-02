package com.junwoo.ott.domain.like.controller;

import com.junwoo.ott.domain.like.dto.request.LikeReadRequestDto;
import com.junwoo.ott.domain.like.dto.request.LikeRequestDto;
import com.junwoo.ott.domain.like.dto.request.LikeToggleRequestDto;
import com.junwoo.ott.domain.like.dto.response.LikeReadResponseDto;
import com.junwoo.ott.domain.like.service.LikeService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api/v1")
@RestController
public class LikeController {

  private final LikeService likeService;

  @PostMapping("/like/{videoId}")
  public void postLike(
      final @PathVariable("videoId") Long videoId,
      final @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    LikeToggleRequestDto request = new LikeToggleRequestDto(userDetails.getUserId(), videoId);
    likeService.toggleLike(request);
  }

  @GetMapping("/me/likes")
  public ResponseDto<Page<LikeReadResponseDto>> getLikedVideos(
      final @AuthenticationPrincipal UserDetailsImpl userDetails,
      final Pageable pageable
  ) {
    LikeReadRequestDto request = new LikeReadRequestDto(userDetails.getUserId(), pageable);
    Page<LikeReadResponseDto> response = likeService.getLikedVideos(request);

    return ResponseDto.ok(response);
  }

  @GetMapping("/me/likes/{videoId}")
  public ResponseDto<Boolean> getLike(
      final @PathVariable("videoId") Long videoId,
      final @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    LikeRequestDto request = new LikeRequestDto(userDetails.getUserId(), videoId);

    return ResponseDto.ok(likeService.getLike(request));
  }

}
