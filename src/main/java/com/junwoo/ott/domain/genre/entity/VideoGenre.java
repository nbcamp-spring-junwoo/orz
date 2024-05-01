package com.junwoo.ott.domain.genre.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Table(uniqueConstraints = {
    @UniqueConstraint(
        name = "VIDEO_GENRE_UNIQUE",
        columnNames = {"video_id", "genre_id"}
    )
})
@Entity
public class VideoGenre {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long videoGenreId;
  @Column(name = "video_id", nullable = false)
  private Long videoId;
  @Column(name = "genre_id", nullable = false)
  private Long genreId;

  public VideoGenre(final Long videoId, final Long genreId) {
    this.videoId = videoId;
    this.genreId = genreId;
  }

}
