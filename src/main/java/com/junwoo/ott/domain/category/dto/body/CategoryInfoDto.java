package com.junwoo.ott.domain.category.dto.body;

import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryInfoDto {

  private final Long categoryId;
  private final CategoryType type;
  private final Set<GenreType> genres;

}
