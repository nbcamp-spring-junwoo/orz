package com.junwoo.ott.domain.like.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class LikeToggleRequestDto {

  private Long userId;
  private Long videoId;

}
