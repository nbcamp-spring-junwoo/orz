package com.junwoo.ott.domain.like.dto.response;

import com.junwoo.ott.domain.like.entity.Like;
import java.time.LocalDateTime;
import lombok.Getter;

@Getter
public class LikeReadResponseDto {

  private final Long videoId;
  private final String title;
  private final String posterUrl;
  private final LocalDateTime registeredAt;

  public LikeReadResponseDto(final Like like) {
    videoId = like.getVideo().getVideoId();
    title = like.getVideo().getTitle();
    posterUrl = like.getVideo().getPosterUrl();
    registeredAt = like.getRegisteredAt();
  }

}
