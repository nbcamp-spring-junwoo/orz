package com.junwoo.ott.domain.like.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class LikeReadRequestDto {

  private Long userId;
  private Pageable pageable;

}
