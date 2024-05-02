package com.junwoo.ott.domain.like.dto.response;

import com.junwoo.ott.domain.like.entity.Like;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.video.entity.Video;
import lombok.Getter;

@Getter
public class LikeToggleResponseDto {

  private final Long likeId;
  private final User user;
  private final Video video;

  public LikeToggleResponseDto(final Like like) {
    this.likeId = like.getId();
    this.user = like.getUser();
    this.video = like.getVideo();
  }

}
