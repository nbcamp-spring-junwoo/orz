package com.junwoo.ott.domain.episode.service;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.junwoo.ott.domain.episode.entity.Episode;
import com.junwoo.ott.domain.episode.repository.EpisodeRepository;
import com.junwoo.ott.domain.episode.test.EpisodeTestValues;
import com.junwoo.ott.domain.user.entity.User;
import com.junwoo.ott.domain.video.entity.Video;
import com.junwoo.ott.global.customenum.MembershipType;
import com.junwoo.ott.global.jwt.UserDetailsImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EpisodeAccessServiceTest implements EpisodeTestValues {

  @Mock
  private EpisodeRepository episodeRepository;
  @Mock
  private Video video;
  @Mock
  private Episode episode;
  @Mock
  private User user;

  @InjectMocks
  private EpisodeAccessService episodeAccessService;

  @BeforeEach
  void setUp() {
    video = Video.builder().videoId(1L).build();
    episode = Episode.builder().episodeId(TEST_EPISODE_ID).membershipType(TEST_MEMBERSHIP_TYPE).video(video).build();
    user = User.builder().membershipType(MembershipType.ROLE_SILVER).username("테스트용 유저").build();

    when(episodeRepository.findById(TEST_EPISODE_ID)).thenReturn(Optional.of(episode));
  }

  @Test
  @DisplayName("비디오에 에피소드가 없을시 거부")
  void 잘못된접근거부() {
    UserDetailsImpl userDetails = new UserDetailsImpl(user);

    boolean canAccess = episodeAccessService.canUserAccessEpisode(userDetails, 1L, TEST_EPISODE_ID);

    assertTrue(canAccess);
    verify(episodeRepository).findById(TEST_EPISODE_ID);
  }

  @Test
  @DisplayName("존재하지 않는 에피소드 접근시 예외처리")
  void 없는에피소드접근() {
    long nonExistentEpisodeId = TEST_EPISODE_ID;
    User user = User.builder().membershipType(MembershipType.ROLE_GOLD).build();
    UserDetailsImpl userDetails = new UserDetailsImpl(user);

    when(episodeRepository.findById(nonExistentEpisodeId)).thenReturn(Optional.empty());

    EntityNotFoundException thrown = assertThrows(
        EntityNotFoundException.class,
        () -> episodeAccessService.canUserAccessEpisode(userDetails, TEST_EPISODE_ID, nonExistentEpisodeId),
        "의도적인 에러가 발생하지 않았습니다."
    );

    assertTrue(thrown.getMessage().contains("에피소드를 찾을 수 없습니다."));

    verify(episodeRepository).findById(nonExistentEpisodeId);
  }

  @Test
  @DisplayName("골드 등급은 모든 에피소드 접근 가능")
  void 골드등급() {
    User goldUser = createUser(MembershipType.ROLE_GOLD);
    UserDetailsImpl userDetails = new UserDetailsImpl(goldUser);

    boolean canAccess = episodeAccessService.canUserAccessEpisode(userDetails, 1L, TEST_EPISODE_ID);

    assertTrue(canAccess, "접근이 허용되었습니다!");
  }

  @Test
  @DisplayName("실버 등급은 골드등급 접근불가")
  void 실버등급() {
    setUpEpisode(MembershipType.ROLE_GOLD);
    User silverUser = createUser(MembershipType.ROLE_SILVER);
    UserDetailsImpl userDetails = new UserDetailsImpl(silverUser);

    boolean canAccess = episodeAccessService.canUserAccessEpisode(userDetails, 1L, TEST_EPISODE_ID);

    assertFalse(canAccess, "맴버쉽 등급이 골드만 접근이 가능합니다!");
  }

  @Test
  @DisplayName("브론즈 등급은 실버등급이상 접근불가")
  void 브론즈등급() {
    setUpEpisode(MembershipType.ROLE_SILVER);
    User bronzeUser = createUser(MembershipType.ROLE_BRONZE);
    UserDetailsImpl userDetails = new UserDetailsImpl(bronzeUser);

    boolean canAccess = episodeAccessService.canUserAccessEpisode(userDetails, 1L, TEST_EPISODE_ID);

    assertFalse(canAccess, "맴버쉽 등급이 실버이상만 접근이 가능합니다!");
  }

  private User createUser(MembershipType membershipType) {
    return User.builder()
        .membershipType(membershipType)
        .username("테스트용 유저")
        .build();
  }

  private void setUpEpisode(MembershipType requiredMembership) {
    Video video = Video.builder().videoId(1L).build();
    Episode episode = Episode.builder().episodeId(TEST_EPISODE_ID).membershipType(requiredMembership).video(video).videoLink(TEST_VIDEO_LINK).build();
    when(episodeRepository.findById(TEST_EPISODE_ID)).thenReturn(Optional.of(episode));
  }

}
