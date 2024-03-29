package com.junwoo.ott.domain.video.common;

import com.junwoo.ott.domain.video.dto.request.VideoCreateRequestDto;
import com.junwoo.ott.global.customenum.RatingType;

public interface VideoTestCommons {

  String TEST_TITLE = "테스트용 제목";
  String TEST_DESCRIPTION = "테스트용 설명";
  RatingType TEST_RATING_TYPE = RatingType.EVERYONE;

  VideoCreateRequestDto TEST_VIDEO_CREATE_REQUEST_DTO = new VideoCreateRequestDto(
      TEST_TITLE,
      TEST_DESCRIPTION,
      TEST_RATING_TYPE
  );

}
