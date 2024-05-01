package com.junwoo.ott.domain.like.dto.response;

import com.junwoo.ott.domain.like.entity.Like;
import lombok.Getter;

@Getter
public class LikeReadResponseDto {

  private final Long videoId;
  private final String videoTitle;
  private final String videoPosterUrl;

  public LikeReadResponseDto(final Like like) {
    videoId = like.getVideo().getVideoId();
    videoTitle = like.getVideo().getTitle();
    videoPosterUrl = like.getVideo().getPosterUrl();
  }

}
