package com.junwoo.ott.domain.announcement.dto.response;

import com.junwoo.ott.domain.announcement.entity.Announcement;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class AnnouncementReadResponseDto {

  private final String title;
  private final String content;
  private final LocalDateTime createdAt;
  private final Coupon coupon;

  public AnnouncementReadResponseDto(Announcement announcement) {
    this.title = announcement.getTitle();
    this.content = announcement.getContent();
    this.createdAt = announcement.getCreatedAt();
    this.coupon = announcement.getCoupon();
  }

}
