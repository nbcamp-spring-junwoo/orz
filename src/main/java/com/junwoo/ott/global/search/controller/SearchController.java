package com.junwoo.ott.global.search.controller;

import com.junwoo.ott.global.common.dto.ResponseDto;
import com.junwoo.ott.global.search.dto.request.VideoSearchRequestDto;
import com.junwoo.ott.global.search.dto.response.SearchResponseDto;
import com.junwoo.ott.global.search.dto.response.VideoSearchResponseDto;
import com.junwoo.ott.global.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v2")
@RequiredArgsConstructor
@RestController
public class SearchController {

  private final SearchService searchService;

  @GetMapping("/complete/search")
  public ResponseDto<SearchResponseDto> search(
      final @RequestParam("input") String input
  ) {
    SearchResponseDto responseDto = searchService.search(input);
    return ResponseDto.ok(responseDto);
  }

  @GetMapping("/videos/search")
  public ResponseDto<VideoSearchResponseDto> searchVideos(
      final @RequestParam String input,
      final @RequestParam(required = false) Integer page
  ) {
    VideoSearchRequestDto dto = new VideoSearchRequestDto(input, page);
    VideoSearchResponseDto responseDto = searchService.getVideosElasticSearch(dto);
    return ResponseDto.ok(responseDto);
  }

}