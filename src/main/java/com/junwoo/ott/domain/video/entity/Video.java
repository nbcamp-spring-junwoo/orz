package com.junwoo.ott.domain.video.entity;

import com.junwoo.ott.global.common.entity.Timestamped;
import com.junwoo.ott.global.customenum.MembershipType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "update video set deleted_at = NOW() where video_id = ?")
@SQLRestriction(value = "deleted_at is NULL")
@Entity
public class Video extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long videoId;
  @Column(nullable = false)
  private String originalTitle;
  @Column(nullable = false)
  private String title;
  @Column(nullable = false, length = 511)
  private String description;
  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private MembershipType membershipType;
  private String posterUrl;
  @Column(nullable = false)
  private LocalDate releasedAt;
  private LocalDateTime deletedAt;

}
