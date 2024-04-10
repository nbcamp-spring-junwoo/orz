package com.junwoo.ott.domain.category.dto.response;

import com.junwoo.ott.domain.category.entity.Category;
import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryReadResponseDto {

    private Long categoryid;
    private CategoryType type;
    private Set<GenreType> genres;

    public CategoryReadResponseDto(Category category) {
        this.categoryid = category.getCategoryId();
        this.type = category.getType();
        this.genres = category.getGenres();
    }

}
