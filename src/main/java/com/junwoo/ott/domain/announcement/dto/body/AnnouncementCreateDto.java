package com.junwoo.ott.domain.announcement.dto.body;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnnouncementCreateDto {

  @Positive
  private final Long couponId;
  @NotNull
  private final String title;
  @NotNull
  private final String content;

}
