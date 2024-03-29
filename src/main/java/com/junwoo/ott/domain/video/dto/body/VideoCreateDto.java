package com.junwoo.ott.domain.video.dto.body;


import com.junwoo.ott.global.customenum.RatingType;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
@AllArgsConstructor
public class VideoCreateDto {
  @NotNull
  private String title;
  @NotNull
  private String description;
  @NotNull
  @Valid
  private RatingType ratingType;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime createdAt;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private LocalDateTime updatedAt;

  public VideoCreateDto(String title, String description, RatingType ratingType) {
    this.title = getTitle();
    this.description = getDescription();
    this.ratingType = getRatingType();
  }

}
