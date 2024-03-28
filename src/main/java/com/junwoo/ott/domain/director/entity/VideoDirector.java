package com.junwoo.ott.domain.director.entity;

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
public class VideoDirector {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long videoDirectorId;

  @ManyToOne
  @JoinColumn(name = "video_id")
  private Video video;

  @ManyToOne
  @JoinColumn(name = "director_id")
  private Director director;

}
