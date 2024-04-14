package com.junwoo.ott.domain.episode.entity;

import com.junwoo.ott.global.common.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import java.time.LocalDate;
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
@Table(uniqueConstraints = {
    @UniqueConstraint(
        name = "EPISODEID_TITLE_UNIQUE",
        columnNames = {"episode_id", "title"}
    )
})
@Entity
public class Episode extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "episode_id")
  private Long episodeId;
  @Column(nullable = false)
  private Long videoId;
  @Column(nullable = false, name = "title")
  private String title;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  private String episodeUrl;
  @Column(nullable = false)
  private LocalDate releasedAt;
  private LocalDateTime deletedAt;

}
