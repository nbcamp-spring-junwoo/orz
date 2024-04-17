package com.junwoo.ott.domain.announcement.dto.request;

import com.junwoo.ott.domain.announcement.dto.body.AnnouncementUpdateDto;
import lombok.Getter;

@Getter
public class AnnouncementUpdateRequestDto {

  private final Long announcementId;
  private final Long couponId;
  private final String title;
  private final String content;

  public AnnouncementUpdateRequestDto(AnnouncementUpdateDto dto, Long announcementId) {
    this.announcementId = announcementId;
    this.couponId = dto.getCouponId();
    this.title = dto.getTitle();
    this.content = dto.getContent();
  }

}
