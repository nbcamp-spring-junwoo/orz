package com.junwoo.ott.domain.video.dto.body;

import com.junwoo.ott.global.customenum.GenreType;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class VideoCreateDto {

  @NotNull
  private String title;
  @NotNull
  private String description;
  private String posterUrl;
  private List<GenreType> genreTypeList;
  @NotNull
  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private String releasedAt;

}
