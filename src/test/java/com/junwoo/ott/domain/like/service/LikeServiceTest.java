package com.junwoo.ott.domain.like.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.junwoo.ott.domain.like.dto.response.LikeReadResponseDto;
import com.junwoo.ott.domain.like.repository.LikeRepository;
import com.junwoo.ott.domain.like.test.LikeServiceTestValues;
import com.junwoo.ott.domain.video.service.VideoService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;

@ExtendWith(MockitoExtension.class)
public class LikeServiceTest implements LikeServiceTestValues {

  @Mock
  private LikeRepository likeRepository;

  @Mock
  private VideoService videoService;

  @InjectMocks
  private LikeService likeService;

  @Nested
  @DisplayName("찜 기능 생성 및 삭제 테스트")
  class ToggleLike {

    @Test
    @DisplayName("찜 기능 생성 성공 테스트")
    void 찜_기능_생성_성공() {
      given(videoService.isExistVideo(any())).willReturn(true);
      given(likeRepository.findLikeByUser_UserIdAndVideo_VideoId(any(), any()))
          .willReturn(Optional.empty());

      likeService.toggleLike(TOGGLE_REQUEST_DTO);

      verify(likeRepository, atLeastOnce()).save(any());
    }

    @Test
    @DisplayName("찜 기능 삭제 성공 테스트")
    void 찜_기능_삭제_성공() {
      given(videoService.isExistVideo(any())).willReturn(true);
      given(likeRepository.findLikeByUser_UserIdAndVideo_VideoId(any(), any()))
          .willReturn(Optional.ofNullable(TEST_LIKE));

      likeService.toggleLike(TOGGLE_REQUEST_DTO);

      verify(likeRepository, atLeastOnce()).delete(any());
    }

    @Test
    @DisplayName("찜 기능 생성 실패 테스트")
    void 찜_기능_생성_실패() {
      given(videoService.isExistVideo(any())).willReturn(false);

      Assertions.assertThrows(EntityNotFoundException.class,
          () -> likeService.toggleLike(TOGGLE_REQUEST_DTO));
    }

  }

  @Nested
  @DisplayName("찜 기능 조회 테스트")
  class ReadLike {

    @Test
    @DisplayName("찜 기능 조회 성공 테스트")
    void 찜_기능_조회_성공() {
      given(likeRepository.findLikeByUser_UserId(any(), any())).willReturn(TEST_LIKES);

      Page<LikeReadResponseDto> response = likeService.getLikedVideos(READ_REQUEST_DTO);
      List<LikeReadResponseDto> result = response.getContent();

      Assertions.assertEquals(result.get(0).getVideoId(), VIDEO_ID);
    }

    @Test
    @DisplayName("찜 기능 확인 성공 테스트")
    void 찜_기능_확인_성공() {
      given(videoService.isExistVideo(any())).willReturn(true);
      given(likeRepository.existsLikeByUser_UserIdAndVideo_VideoId(any(), any())).willReturn(true);

      Boolean result = likeService.getLike(REQUEST_DTO);

      Assertions.assertEquals(result, Boolean.TRUE);
    }

    @Test
    @DisplayName("찜 기능 조회 실패 테스트")
    void 찜_기능_조회_실패() {
      given(videoService.isExistVideo(any())).willReturn(false);

      Assertions.assertThrows(EntityNotFoundException.class,
          () -> likeService.getLike(REQUEST_DTO));
    }

  }

}
