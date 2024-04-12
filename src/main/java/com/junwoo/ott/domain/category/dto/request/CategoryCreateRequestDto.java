package com.junwoo.ott.domain.category.dto.request;

import com.junwoo.ott.domain.category.dto.body.CategoryCreateDto;
import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryCreateRequestDto {

  private final CategoryType type;
  private final Set<GenreType> genres;

  public CategoryCreateRequestDto(CategoryCreateDto dto) {
    this.type = dto.getType();
    this.genres = dto.getGenres();
  }

}
