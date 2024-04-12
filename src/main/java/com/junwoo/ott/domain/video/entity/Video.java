package com.junwoo.ott.domain.video.entity;


import com.junwoo.ott.domain.category.entity.VideoCategory;
import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.global.common.entity.Timestamped;
import com.junwoo.ott.global.customenum.RatingType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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

  @OneToMany(mappedBy = "video", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<VideoCategory> videoCategories = new HashSet<>();

  public static Video of(final VideoCreateRequestDto dto) {

    return Video
        .builder()
        .title(dto.getTitle())
        .description(dto.getDescription())
        .ratingType(dto.getRatingType())
        .build();
  }

  public VideoBuilder toBuilder() {
    return Video
        .builder()
        .videoId(this.videoId)
        .title(this.title)
        .description(this.description)
        .ratingType(this.ratingType)
        .deletedAt(this.deletedAt);
  }

  public void update(String title, String description, RatingType ratingType) {
    if (title != null) {
      this.title = title;
    }
    if (description != null) {
      this.description = description;
    }
    if (ratingType != null) {
      this.ratingType = ratingType;
    }

  }

  public void addVideoCategory(VideoCategory videoCategory) {
    if (this.videoCategories == null) {
      this.videoCategories = new HashSet<>();
    }
    this.videoCategories.add(videoCategory);
  }

  public void removeVideoCategory(VideoCategory videoCategory) {
    this.videoCategories.remove(videoCategory);
    videoCategory.setVideo(null);
  }

}
