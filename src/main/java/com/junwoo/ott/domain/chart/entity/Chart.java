package com.junwoo.ott.domain.chart.entity;

import com.junwoo.ott.domain.video.entity.Video;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
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
public class Chart {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long chartId;
  @OneToOne
  @JoinColumn(name = "video_id")
  private Video video;

}
