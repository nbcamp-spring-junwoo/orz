package com.junwoo.ott.domain.category.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Pageable;

@Getter
@AllArgsConstructor
public class CategoryReadRequestDto {

    private Pageable pageable;

}
