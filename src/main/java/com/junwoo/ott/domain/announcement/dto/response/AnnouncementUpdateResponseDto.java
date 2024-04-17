package com.junwoo.ott.domain.announcement.dto.response;

import com.junwoo.ott.domain.announcement.entity.Announcement;
import lombok.Getter;

@Getter
public class AnnouncementUpdateResponseDto {

  private final Long announcementId;
  private final Long couponId;
  private final String title;
  private final String content;

  public AnnouncementUpdateResponseDto(final Announcement announcement) {
    this.announcementId = announcement.getAnnouncementId();
    this.couponId = announcement.getCoupon().getCouponId();
    this.title = announcement.getTitle();
    this.content = announcement.getContent();
  }

}
