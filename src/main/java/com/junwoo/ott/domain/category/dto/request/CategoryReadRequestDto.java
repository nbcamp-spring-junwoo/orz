package com.junwoo.ott.domain.category.dto.request;

import org.springframework.data.domain.Pageable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CategoryReadRequestDto {

    private Pageable pageable;

}
