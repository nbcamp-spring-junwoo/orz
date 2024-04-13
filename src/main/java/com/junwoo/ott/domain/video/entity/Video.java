package com.junwoo.ott.domain.video.entity;


import com.junwoo.ott.global.common.entity.Timestamped;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@SQLDelete(sql = "update video set deleted_at = NOW() where video_id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@Entity
public class Video extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long videoId;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false, length = 511)
  private String description;
  private String posterUrl;
  @Column(nullable = false)
  private LocalDate releasedAt;
  private LocalDateTime deletedAt;

}
