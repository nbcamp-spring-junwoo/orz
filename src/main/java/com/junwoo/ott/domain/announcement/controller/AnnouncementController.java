package com.junwoo.ott.domain.announcement.controller;

import com.junwoo.ott.domain.announcement.dto.request.AnnouncementReadRequestDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementReadResponseDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementsReadResponseDto;
import com.junwoo.ott.domain.announcement.service.AnnouncementService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/announcement")
@RequiredArgsConstructor
@RestController
public class AnnouncementController {

  private final AnnouncementService announcementService;

  @GetMapping("/{announcementId}")
  public ResponseDto<AnnouncementReadResponseDto> getAnnouncement(
      final @PathVariable("announcementId") Long announcementId
  ) {
    AnnouncementReadRequestDto dto = new AnnouncementReadRequestDto(announcementId);
    return ResponseDto.ok(announcementService.getAnnouncement(dto));
  }

  @GetMapping
  public ResponseDto<Page<AnnouncementsReadResponseDto>> getAnnouncementList(
      final @RequestParam("page") int page
  ) {
    return ResponseDto.ok(announcementService.getAnnouncementList(page));
  }

}
