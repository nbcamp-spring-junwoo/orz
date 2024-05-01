package com.junwoo.ott.domain.like.entity;

import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.video.entity.Video;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Like {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private User user;
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "video_id")
  private Video video;
  // TODO: 날짜 필드를 추가하여 찜 목록을 조회할 때, 찜 한 기준으로 최신순으로 조회하는 것이 좋을까?

  public static Like of(final User user, final Video video) {

    return Like.builder()
        .user(user)
        .video(video)
        .build();
  }

}
