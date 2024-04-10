package com.junwoo.ott.domain.category.service;

import com.junwoo.ott.domain.category.dto.request.CategoryCreateRequestDto;
import com.junwoo.ott.domain.category.dto.request.CategoryReadRequestDto;
import com.junwoo.ott.domain.category.dto.request.CategoryUpdateRequestDto;
import com.junwoo.ott.domain.category.dto.response.CategoryCreateResponseDto;
import com.junwoo.ott.domain.category.dto.response.CategoryReadResponseDto;
import com.junwoo.ott.domain.category.dto.response.CategoryUpdateResponseDto;
import com.junwoo.ott.domain.category.entity.Category;
import com.junwoo.ott.domain.category.repository.CategoryJpaRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class CategoryService {

  private final CategoryJpaRepository categoryJpaRepository;

  public CategoryCreateResponseDto createCategory(final CategoryCreateRequestDto dto) {
    Category category = Category.builder()
        .type(dto.getType())
        .genres(dto.getGenres())
        .build();

    category = categoryJpaRepository.save(category);

    return new CategoryCreateResponseDto(category);
  }

  @Transactional(readOnly = true)
  public Page<CategoryReadResponseDto> getCategories(final CategoryReadRequestDto dto) {
    Page<Category> categories = categoryJpaRepository.findAllByOrderByIdAsc(dto.getPageable());

    return categories.map(CategoryReadResponseDto::new);
  }

  public CategoryUpdateResponseDto updateCategory(final CategoryUpdateRequestDto dto) {
    Category category = categoryJpaRepository.findById(dto.getCategoryId())
        .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다."));

    category.updateCategoryDetails(dto.getDto().getType(), dto.getDto().getGenres());
    category = categoryJpaRepository.save(category);

    return new CategoryUpdateResponseDto(category);
  }

  public void deleteCategory(final Long categoryId) {
    Category category = categoryJpaRepository.findById(categoryId)
        .orElseThrow(() -> new EntityNotFoundException("카테고리를 찾을 수 없습니다."));

    categoryJpaRepository.delete(category);
  }

  public Category existsCategoryById(final Long categoryId) {

    return categoryJpaRepository.findById(categoryId).orElseThrow(
        () -> new EntityNotFoundException("해당 카테고리가 존재하지 않습니다.")
    );
  }

}
