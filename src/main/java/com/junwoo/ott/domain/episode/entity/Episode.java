package com.junwoo.ott.domain.episode.entity;

import com.junwoo.ott.domain.episode.dto.body.EpisodeUpdateDto;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.global.common.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update episode set deleted_at = NOW() where episode_id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@Entity
public class Episode extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long episodeId;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false)
  private LocalDateTime releasedAt;
  private LocalDateTime deletedAt;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "video_id")
  private Video video;

  public void update(final EpisodeUpdateDto dto) {

    if (dto.getTitle() != null) {
      this.title = dto.getTitle();
    }
    if (dto.getReleasedAt() != null) {
      this.releasedAt = dto.getReleasedAt();
    }

  }

}
