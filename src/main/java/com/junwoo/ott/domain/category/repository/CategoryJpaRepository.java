package com.junwoo.ott.domain.category.repository;

import com.junwoo.ott.domain.category.entity.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CategoryJpaRepository extends JpaRepository<Category, Long> {

  @Query("SELECT c FROM Category c ORDER BY c.categoryId ASC")
  Page<Category> findAllByOrderByIdAsc(Pageable pageable);

}
