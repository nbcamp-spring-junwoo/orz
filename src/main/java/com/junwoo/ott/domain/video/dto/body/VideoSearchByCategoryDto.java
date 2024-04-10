package com.junwoo.ott.domain.video.dto.body;

import com.junwoo.ott.global.customenum.CategoryType;
import com.junwoo.ott.global.customenum.GenreType;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class VideoSearchByCategoryDto {
    private final CategoryType categoryType;
    private final Set<GenreType> genres;

}
