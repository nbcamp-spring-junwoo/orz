package com.junwoo.ott.domain.announcement.dto.body;

import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnnouncementUpdateDto {

  @Positive
  private final Long couponId;
  private final String title;
  private final String content;

}
