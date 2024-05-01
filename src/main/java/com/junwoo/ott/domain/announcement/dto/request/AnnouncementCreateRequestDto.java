package com.junwoo.ott.domain.announcement.dto.request;

import com.junwoo.ott.domain.announcement.dto.body.AnnouncementCreateDto;
import lombok.Getter;

@Getter
public class AnnouncementCreateRequestDto {

  private final Long couponId;
  private final String title;
  private final String content;

  public AnnouncementCreateRequestDto(final AnnouncementCreateDto dto) {
    this.couponId = dto.getCouponId();
    this.title = dto.getTitle();
    this.content = dto.getContent();
  }

}
