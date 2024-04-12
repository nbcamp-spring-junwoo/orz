package com.junwoo.ott.global.search.controller;

import com.junwoo.ott.global.common.dto.ResponseDto;
import com.junwoo.ott.global.search.dto.response.SearchResponseDto;
import com.junwoo.ott.global.search.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/complete/search")
@RequiredArgsConstructor
@RestController
public class SearchController {

  private final SearchService searchService;

  @GetMapping
  public ResponseDto<SearchResponseDto> search(
      final @RequestParam("input") String input
  ) {
    SearchResponseDto responseDto = searchService.search(input);
    return ResponseDto.ok(responseDto);
  }

}
