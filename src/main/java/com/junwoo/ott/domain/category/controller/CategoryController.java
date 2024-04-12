package com.junwoo.ott.domain.category.controller;

import com.junwoo.ott.domain.category.dto.body.CategoryCreateDto;
import com.junwoo.ott.domain.category.dto.body.CategoryUpdateDto;
import com.junwoo.ott.domain.category.dto.request.CategoryCreateRequestDto;
import com.junwoo.ott.domain.category.dto.request.CategoryReadRequestDto;
import com.junwoo.ott.domain.category.dto.request.CategoryUpdateRequestDto;
import com.junwoo.ott.domain.category.dto.response.CategoryReadResponseDto;
import com.junwoo.ott.domain.category.service.CategoryService;
import com.junwoo.ott.global.common.dto.ResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController {

  private final CategoryService categoryService;

  @PostMapping
  public void postCategory(final @Validated @RequestBody CategoryCreateDto dto) {
    CategoryCreateRequestDto createDto = new CategoryCreateRequestDto(dto);
    categoryService.createCategory(createDto);
  }

  @GetMapping
  public ResponseDto<Page<CategoryReadResponseDto>> getCategories(final Pageable pageable) {

    CategoryReadRequestDto dto = new CategoryReadRequestDto(pageable);
    Page<CategoryReadResponseDto> result = categoryService.getCategories(dto);

    return ResponseDto.ok(result);
  }

  @PutMapping("/{categoryId}")
  public void putCategory(
      @PathVariable final Long categoryId, @RequestBody final CategoryUpdateDto dto
  ) {
    CategoryUpdateRequestDto updateRequestDto = new CategoryUpdateRequestDto(categoryId, dto);
    categoryService.updateCategory(updateRequestDto);
  }

  @DeleteMapping("/{categoryId}")
  public void deleteCategory(@PathVariable final Long categoryId) {
    categoryService.deleteCategory(categoryId);
  }

}
