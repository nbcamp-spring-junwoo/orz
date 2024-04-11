package com.junwoo.ott.domain.category.service;

import com.junwoo.ott.domain.category.dto.body.CategoryUpdateDto;
import com.junwoo.ott.domain.category.dto.request.CategoryCreateRequestDto;
import com.junwoo.ott.domain.category.dto.request.CategoryReadRequestDto;
import com.junwoo.ott.domain.category.dto.request.CategoryUpdateRequestDto;
import com.junwoo.ott.domain.category.dto.response.CategoryCreateResponseDto;
import com.junwoo.ott.domain.category.dto.response.CategoryReadResponseDto;
import com.junwoo.ott.domain.category.dto.response.CategoryUpdateResponseDto;
import com.junwoo.ott.domain.category.entity.Category;
import com.junwoo.ott.domain.category.repository.CategoryJpaRepository;
import com.junwoo.ott.domain.category.test.CategoryTestValues;
import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest implements CategoryTestValues {

  @Mock
  private CategoryJpaRepository categoryJpaRepository;

  @InjectMocks
  private CategoryService categoryService;

  @Test
  @DisplayName("카테고리 생성")
  void 카테고리생성성공() {
    CategoryCreateRequestDto requestDto = new CategoryCreateRequestDto(TEST_CATEGORY_TYPE, TEST_GENRES);

    Category category = Category.builder()
        .type(TEST_CATEGORY_TYPE)
        .genres(TEST_GENRES)
        .build();

    when(categoryJpaRepository.save(any(Category.class))).thenReturn(category);

    CategoryCreateResponseDto responseDto = categoryService.createCategory(requestDto);

    assertNotNull(responseDto);
    assertEquals(TEST_CATEGORY_TYPE, responseDto.getType());
    assertEquals(TEST_GENRES, responseDto.getGenres());
    verify(categoryJpaRepository).save(any(Category.class));
  }

  @Test
  @DisplayName("카테고리 조회")
  void 카테고리조회성공() {
    CategoryReadRequestDto requestDto = new CategoryReadRequestDto(TEST_PAGEABLE);
    Category category = Category.builder()
        .categoryId(TEST_CATEGORY_ID)
        .type(TEST_CATEGORY_TYPE)
        .genres(TEST_GENRES)
        .build();
    Page<Category> categoryPage = new PageImpl<>(Collections.singletonList(category));

    when(categoryJpaRepository.findAllByOrderByIdAsc(TEST_PAGEABLE)).thenReturn(categoryPage);

    Page<CategoryReadResponseDto> response = categoryService.getCategories(requestDto);

    assertNotNull(response);
    assertFalse(response.isEmpty());
    verify(categoryJpaRepository).findAllByOrderByIdAsc(TEST_PAGEABLE);
  }

  @Test
  @DisplayName("카테고리 수정")
  void 카테고리수정성공() {
    CategoryUpdateRequestDto updateRequestDto = new CategoryUpdateRequestDto(
        TEST_CATEGORY_ID,
        new CategoryUpdateDto(TEST_CATEGORY_TYPE, new HashSet<>(TEST_GENRES))
    );

    Category existingCategory = Category.builder()
        .categoryId(TEST_CATEGORY_ID)
        .type(CategoryType.ENTERTAINMENT)
        .genres(new HashSet<>(Set.of(GenreType.BLACK_COMEDY)))
        .build();

    when(categoryJpaRepository.findById(TEST_CATEGORY_ID)).thenReturn(Optional.of(existingCategory));
    when(categoryJpaRepository.save(any(Category.class))).thenReturn(existingCategory);

    CategoryUpdateResponseDto responseDto = categoryService.updateCategory(updateRequestDto);

    assertNotNull(responseDto);
    assertEquals(TEST_CATEGORY_TYPE, responseDto.getType());
    assertTrue(responseDto.getGenres().containsAll(TEST_GENRES));
    verify(categoryJpaRepository).findById(TEST_CATEGORY_ID);
    verify(categoryJpaRepository).save(any(Category.class));
  }

  @Test
  @DisplayName("카테고리 삭제")
  void 카테고리삭제성공() {
    Category category = Category.builder()
        .categoryId(TEST_CATEGORY_ID)
        .type(TEST_CATEGORY_TYPE)
        .genres(TEST_GENRES)
        .build();

    when(categoryJpaRepository.findById(TEST_CATEGORY_ID)).thenReturn(Optional.of(category));

    categoryService.deleteCategory(TEST_CATEGORY_ID);

    verify(categoryJpaRepository).findById(TEST_CATEGORY_ID);
    verify(categoryJpaRepository).delete(category);
  }

  @Test
  @DisplayName("id 조회")
  void id조회성공() {
    Category category = Category.builder()
        .categoryId(TEST_CATEGORY_ID)
        .type(TEST_CATEGORY_TYPE)
        .genres(TEST_GENRES)
        .build();

    when(categoryJpaRepository.findById(TEST_CATEGORY_ID)).thenReturn(Optional.of(category));

    Category foundCategory = categoryService.existsCategoryById(TEST_CATEGORY_ID);

    assertNotNull(foundCategory);
    assertEquals(TEST_CATEGORY_ID, foundCategory.getCategoryId());
    verify(categoryJpaRepository).findById(TEST_CATEGORY_ID);
  }

}
