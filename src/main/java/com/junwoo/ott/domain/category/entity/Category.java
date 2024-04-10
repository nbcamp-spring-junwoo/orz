package com.junwoo.ott.domain.category.entity;

import com.junwoo.ott.global.common.entity.Timestamped;
import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Category extends Timestamped {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long categoryId;
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private CategoryType type;
  @ElementCollection(targetClass = GenreType.class, fetch = FetchType.EAGER)
  @CollectionTable(name = "category_genres", joinColumns = @JoinColumn(name = "category_id"))
  @Enumerated(EnumType.STRING)
  @Column(name = "genre")
  private Set<GenreType> genres = new HashSet<>();

  @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<VideoCategory> videoCategories = new HashSet<>();

  public void updateCategoryDetails(CategoryType type, Set<GenreType> genres) {
    this.type = type;
    this.genres.clear();
    if (genres != null) {
      this.genres.addAll(genres);
    }
  }

}
