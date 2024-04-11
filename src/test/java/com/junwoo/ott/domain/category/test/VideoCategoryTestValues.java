package com.junwoo.ott.domain.category.test;

import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface VideoCategoryTestValues {

    Long TEST_VIDEO_ID = 1L;
    Long TEST_CATEGORY_ID = 2L;

    CategoryType TEST_CATEGORY_TYPE = CategoryType.DRAMA;
    Set<GenreType> TEST_GENRES = Set.of(GenreType.COMEDY, GenreType.DOCUMENTARY);

    Integer TEST_PAGE = 0;
    Integer TEST_SIZE = 10;
    Pageable TEST_PAGEABLE = PageRequest.of(TEST_PAGE, TEST_SIZE);
}
