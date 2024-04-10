package com.junwoo.ott.domain.category.dto.request;

import com.junwoo.ott.domain.category.dto.body.CategoryUpdateDto;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryUpdateRequestDto {

    private final Long categoryId;
    private final CategoryUpdateDto dto;

}
