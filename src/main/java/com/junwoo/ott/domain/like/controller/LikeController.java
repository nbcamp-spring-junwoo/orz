package com.junwoo.ott.domain.like.controller;

import com.junwoo.ott.domain.like.dto.request.LikeCreateRequestDto;
import com.junwoo.ott.domain.like.dto.request.LikeReadRequestDto;
import com.junwoo.ott.domain.like.dto.request.LikeRequestDto;
import com.junwoo.ott.domain.like.dto.response.LikeReadResponseDto;
import com.junwoo.ott.domain.like.service.LikeService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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
    LikeCreateRequestDto request = new LikeCreateRequestDto(userDetails.getUserId(), videoId);
    likeService.createLike(request);
  }

  @GetMapping("/me/likes")
  public ResponseDto<Page<LikeReadResponseDto>> getLikes(
      final @AuthenticationPrincipal UserDetailsImpl userDetails,
      final Pageable pageable
  ) {
    LikeReadRequestDto request = new LikeReadRequestDto(userDetails.getUserId(), pageable);
    Page<LikeReadResponseDto> response = likeService.getLikes(request);

    return ResponseDto.ok(response);
  }

  @GetMapping("/me/likes/{videoId}")
  public boolean getLike(
      final @PathVariable("videoId") Long videoId,
      final @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    LikeRequestDto request = new LikeRequestDto(userDetails.getUserId(), videoId);

    return likeService.getLike(request);
  }

  @DeleteMapping("/me/likes/{videoId}")
  public void deleteLike(
      final @PathVariable("videoId") Long videoId,
      final @AuthenticationPrincipal UserDetailsImpl userDetails
  ) {
    LikeRequestDto request = new LikeRequestDto(userDetails.getUserId(), videoId);
    likeService.deleteLike(request);
  }

}
