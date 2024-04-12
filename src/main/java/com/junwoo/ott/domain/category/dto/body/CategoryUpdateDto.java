package com.junwoo.ott.domain.category.dto.body;

import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryUpdateDto {

  @NotNull
  private final CategoryType type;
  @NotEmpty
  private final Set<GenreType> genres;

}
