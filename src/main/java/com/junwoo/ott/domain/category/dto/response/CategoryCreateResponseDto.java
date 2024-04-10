package com.junwoo.ott.domain.category.dto.response;

import com.junwoo.ott.domain.category.entity.Category;
import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryCreateResponseDto {
    private final Long categoryid;
    private final CategoryType type;
    private final Set<GenreType> genres;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    public CategoryCreateResponseDto(Category category) {
        this.categoryid = category.getCategoryId();
        this.type = category.getType();
        this.genres = category.getGenres();
        this.createdAt = category.getCreatedAt();
        this.updatedAt = category.getUpdatedAt();
    }
    
}
