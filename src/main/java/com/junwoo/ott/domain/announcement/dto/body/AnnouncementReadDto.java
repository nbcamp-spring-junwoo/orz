package com.junwoo.ott.domain.announcement.dto.body;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnnouncementReadDto {

  private Long announcementId;
  private String title;

}
