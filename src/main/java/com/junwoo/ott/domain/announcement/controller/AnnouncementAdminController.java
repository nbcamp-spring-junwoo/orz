package com.junwoo.ott.domain.announcement.controller;

import com.junwoo.ott.domain.announcement.dto.body.AnnouncementCreateDto;
import com.junwoo.ott.domain.announcement.dto.body.AnnouncementUpdateDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementCreateRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementDeleteRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementUpdateRequestDto;
import com.junwoo.ott.domain.announcement.service.AnnouncementService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api/v1/admin/announcement")
@RequiredArgsConstructor
@RestController
public class AnnouncementAdminController {

  private final AnnouncementService announcementService;

  @PostMapping
  public void postAnnouncement(final @Validated @RequestBody AnnouncementCreateDto dto) {
    AnnouncementCreateRequestDto createRequestDto = new AnnouncementCreateRequestDto(dto);
    announcementService.createAnnouncement(createRequestDto);
  }

  @PutMapping("/{announcementId}")
  public void updateAnnouncement(
      final @RequestBody AnnouncementUpdateDto dto,
      final @PathVariable("announcementId") Long announcementId
  ) {
    AnnouncementUpdateRequestDto updateRequestDto = new AnnouncementUpdateRequestDto(dto,
        announcementId);
    announcementService.updateAnnouncement(updateRequestDto);
  }

  @DeleteMapping("/{announcementId}")
  public void deleteAnnouncement(final @PathVariable("announcementId") Long announcementId) {
    AnnouncementDeleteRequestDto announcementDeleteRequestDto = new AnnouncementDeleteRequestDto(
        announcementId);
    announcementService.deleteAnnouncement(announcementDeleteRequestDto);
  }

}
