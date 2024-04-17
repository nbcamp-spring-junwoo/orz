package com.junwoo.ott.domain.announcement.dto.body;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnnouncementUpdateDto {

  private final Long couponId;
  private final String title;
  private final String content;

}
