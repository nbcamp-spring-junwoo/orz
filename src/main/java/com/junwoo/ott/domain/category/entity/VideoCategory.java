package com.junwoo.ott.domain.category.entity;

import com.junwoo.ott.domain.video.entity.Video;
import jakarta.persistence.Entity;
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
public class VideoCategory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long videoCategoryId;

  @ManyToOne
  @JoinColumn(name = "video_id", nullable = false)
  private Video video;

  @ManyToOne
  @JoinColumn(name = "category_id", nullable = false)
  private Category category;

  public VideoCategory(Video video, Category category) {
    this.video = video;
    this.category = category;
  }

  public void setVideo(Video video) {
    this.video = video;
  }

}
