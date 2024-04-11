package com.junwoo.ott.domain.video.test;

import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.global.customenum.RatingType;
import java.time.LocalDateTime;
import java.util.Set;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

public interface VideoTestValues {

  Long TEST_VIDEO_ID = 1L;
  String TEST_TITLE = "테스트용 제목";
  String TEST_DESCRIPTION = "테스트용 설명";
  RatingType TEST_RATING_TYPE = RatingType.EVERYONE;
  LocalDateTime TEST_VIDEO_CREATED_AT = LocalDateTime.of(2024, 3, 1, 0, 0);
  LocalDateTime TEST_VIDEO_UPDATED_AT = LocalDateTime.now();
  Integer TEST_PAGE = 0;
  Integer TEST_SIZE = 10;
  Set<Long> TEST_CATEGORY_IDS = Set.of(1L, 4L);


  Pageable TEST_PAGEABLE = PageRequest.of(TEST_PAGE, TEST_SIZE);
  VideoCreateRequestDto TEST_VIDEO_CREATE_REQUEST_DTO = new VideoCreateRequestDto(
      TEST_TITLE,
      TEST_DESCRIPTION,
      TEST_RATING_TYPE,
      TEST_CATEGORY_IDS
  );

}
