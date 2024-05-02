package com.junwoo.ott.domain.like.service;

import com.junwoo.ott.domain.like.dto.request.LikeReadRequestDto;
import com.junwoo.ott.domain.like.dto.request.LikeRequestDto;
import com.junwoo.ott.domain.like.dto.request.LikeToggleRequestDto;
import com.junwoo.ott.domain.like.dto.response.LikeReadResponseDto;
import com.junwoo.ott.domain.like.entity.Like;
import com.junwoo.ott.domain.like.repository.LikeRepository;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.domain.video.service.VideoService;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class LikeService {

  private final LikeRepository likeRepository;

  private final VideoService videoService;

  public void toggleLike(final LikeToggleRequestDto dto) {
    if (!videoService.isExistVideo(dto.getVideoId())) {
      throw new EntityNotFoundException("해당 비디오가 존재하지 않습니다.");
    }

    Optional<Like> like = likeRepository.findLikeByUser_UserIdAndVideo_VideoId(dto.getUserId(),
        dto.getVideoId());

    if (like.isPresent()) {
      likeRepository.delete(like.get());
      return;
    }

    User user = User.builder().userId(dto.getUserId()).build();
    Video video = Video.builder().videoId(dto.getVideoId()).build();
    Like createLike = Like.of(user, video);
    likeRepository.save(createLike);
  }

  @Transactional(readOnly = true)
  public Boolean getLike(final LikeRequestDto dto) {
    if (!videoService.isExistVideo(dto.getVideoId())) {
      throw new EntityNotFoundException("해당 비디오가 존재하지 않습니다.");
    }

    return likeRepository.existsLikeByUser_UserIdAndVideo_VideoId(dto.getUserId(),
        dto.getVideoId());
  }

  @Transactional(readOnly = true)
  public Page<LikeReadResponseDto> getLikedVideos(final LikeReadRequestDto dto) {
    Page<Like> likes = likeRepository.findLikeByUser_UserId(dto.getUserId(), dto.getPageable());

    return likes.map(LikeReadResponseDto::new);
  }

}
