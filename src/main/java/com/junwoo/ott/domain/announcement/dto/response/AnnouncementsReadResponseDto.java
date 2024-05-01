package com.junwoo.ott.domain.announcement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnnouncementsReadResponseDto {

  private Long announcementId;
  private String title;

}
