package com.junwoo.ott.domain.like.test;

import com.junwoo.ott.domain.like.dto.request.LikeReadRequestDto;
import com.junwoo.ott.domain.like.dto.request.LikeRequestDto;
import com.junwoo.ott.domain.like.dto.request.LikeToggleRequestDto;
import com.junwoo.ott.domain.like.entity.Like;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.video.entity.Video;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

public interface LikeServiceTestValues {

  Long USER_ID = 1L;
  Long VIDEO_ID = 1L;
  int PAGE_SIZE = 10;
  int PAGE_NUMBER = 1;


  LikeToggleRequestDto TOGGLE_REQUEST_DTO = new LikeToggleRequestDto(USER_ID, VIDEO_ID);

  LikeReadRequestDto READ_REQUEST_DTO = new LikeReadRequestDto(USER_ID,
      PageRequest.of(PAGE_NUMBER, PAGE_SIZE));

  LikeRequestDto REQUEST_DTO = new LikeRequestDto(USER_ID, VIDEO_ID);

  Like TEST_LIKE = Like.of(User.builder().userId(USER_ID).build(),
      Video.builder().videoId(VIDEO_ID).build());

  Page<Like> TEST_LIKES = new PageImpl<>(List.of(TEST_LIKE));

}
