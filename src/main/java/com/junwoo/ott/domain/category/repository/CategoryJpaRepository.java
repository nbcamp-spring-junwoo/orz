package com.junwoo.ott.domain.category.repository;

import com.junwoo.ott.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<Category, Integer> {

}
