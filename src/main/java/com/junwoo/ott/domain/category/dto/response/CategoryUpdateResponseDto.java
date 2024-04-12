package com.junwoo.ott.domain.category.dto.response;

import com.junwoo.ott.domain.category.entity.Category;
import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryUpdateResponseDto {

  private final Long categoryId;
    private final CategoryType type;
    private final Set<GenreType> genres;
    private final LocalDateTime updatedAt;

    public CategoryUpdateResponseDto(final Category category) {
      this.categoryId = category.getCategoryId();
        this.type = category.getType();
        this.genres = new HashSet<>(category.getGenres());
        this.updatedAt = category.getUpdatedAt();
    }

}
