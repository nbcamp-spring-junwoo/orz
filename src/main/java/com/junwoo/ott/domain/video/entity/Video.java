package com.junwoo.ott.domain.video.entity;


import com.junwoo.ott.domain.video.dto.body.VideoUpdateDto;
import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.global.common.entity.Timestamped;
import com.junwoo.ott.global.customenum.RatingType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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
  private String title;
  @Column(nullable = false)
  private String description;
  @Column(nullable = false)
  @Enumerated(EnumType.ORDINAL)
  private RatingType ratingType;
  private LocalDateTime deletedAt;

  public static Video of(VideoCreateRequestDto dto) {

    return Video.builder()
        .title(dto.getTitle())
        .description(dto.getDescription())
        .ratingType(dto.getRatingType())
        .build();
  }

  public VideoBuilder toBuilder() {
    return Video.builder()
        .videoId(this.videoId)
        .title(this.title)
        .description(this.description)
        .ratingType(this.ratingType)
        .deletedAt(this.deletedAt);
  }

  public Video update(VideoUpdateDto dto) {
    VideoBuilder builder = this.toBuilder();

    if (dto.getTitle() != null) {
      builder.title(dto.getTitle());
    }

    if (dto.getDescription() != null) {
      builder.description(dto.getDescription());
    }

    if (dto.getRatingType() != null) {
      builder.ratingType(dto.getRatingType());
    }

    return builder.build();
  }

}
