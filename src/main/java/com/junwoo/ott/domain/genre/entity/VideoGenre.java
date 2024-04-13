package com.junwoo.ott.domain.genre.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class VideoGenre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long VideoGenreId;
  @Column(nullable = false)
  private Long videoId;
  @Column(nullable = false)
  private Long genreId;

  public VideoGenre(final Long videoId, final Long genreId) {
    this.videoId = videoId;
    this.genreId = genreId;
  }

}
