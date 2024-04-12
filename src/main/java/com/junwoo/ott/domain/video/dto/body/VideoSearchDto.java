package com.junwoo.ott.domain.video.dto.body;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoSearchDto {

  private Long video_id;
  private String title;
  private String description;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDateTime created_at;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDateTime updated_at;
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  private LocalDateTime deleted_at;
  private int rating_type;

}
