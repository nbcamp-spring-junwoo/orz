package com.junwoo.ott.domain.announcement.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.verify;

import com.junwoo.ott.domain.announcement.dto.request.AnnouncementCreateRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementDeleteRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementReadRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementUpdateRequestDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementCreateResponseDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementReadResponseDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementUpdateResponseDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementsReadResponseDto;
import com.junwoo.ott.domain.announcement.repository.AnnouncementRepository;
import com.junwoo.ott.domain.announcement.test.AnnouncementTestValues;
import com.junwoo.ott.domain.coupon.service.CouponService;
import jakarta.persistence.EntityNotFoundException;
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
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class AnnouncementServiceTest implements AnnouncementTestValues {

  @Mock
  private CouponService couponService;
  @Mock
  private AnnouncementRepository announcementRepository;

  @InjectMocks
  private AnnouncementService announcementService;

  @Nested
  @DisplayName("공지사항 생성 테스트")
  class CreateAnnouncement {

    @Test
    @DisplayName("공지사항 생성 테스트 성공")
    void 공지사항_생성_성공_테스트() {
      AnnouncementCreateRequestDto dto = TEST_ANNOUNCEMENT_CREATE_REQUEST_DTO_V1;

      given(couponService.existCouponById(any())).willReturn(TEST_COUPON_V3);
      given(announcementRepository.save(any())).willReturn(TEST_ANNOUNCEMENT_V3);

      AnnouncementCreateResponseDto result = announcementService.createAnnouncement(dto);

      Assertions.assertEquals(result.getCouponId(), dto.getCouponId());
      Assertions.assertEquals(result.getContent(), dto.getContent());
      Assertions.assertEquals(result.getTitle(), dto.getTitle());
    }

    @Test
    @DisplayName("공지사항 생성 실패 테스트")
    void 공지사항_생성_실패_테스트() {
      AnnouncementCreateRequestDto dto = TEST_ANNOUNCEMENT_CREATE_REQUEST_DTO_V1;

      given(couponService.existCouponById(any())).willThrow(EntityNotFoundException.class);

      Assertions.assertThrows(EntityNotFoundException.class,
          () -> announcementService.createAnnouncement(dto));
    }

  }

  @Nested
  @DisplayName("공지사항 수정 테스트")
  class UpdateAnnouncement {

    @Test
    @DisplayName("공지사항 수정 테스트 성공")
    void 공지사항_수정_성공_테스트() {
      AnnouncementUpdateRequestDto dto = TEST_ANNOUNCEMENT_UPDATE_REQUEST_DTO_V1;

      given(couponService.existCouponById(any())).willReturn(TEST_COUPON_V2);
      given(announcementRepository.findById(any())).willReturn(
          Optional.ofNullable(TEST_ANNOUNCEMENT_V1));

      AnnouncementUpdateResponseDto result = announcementService.updateAnnouncement(dto);

      Assertions.assertEquals(result.getCouponId(), dto.getCouponId());
      Assertions.assertEquals(result.getContent(), dto.getContent());
      Assertions.assertEquals(result.getTitle(), dto.getTitle());
    }

    @Test
    @DisplayName("공지사항 수정 테스트 실패, 공지사항 존재 X")
    void 공지사항_수정_실패_테스트_공지사항_존재X() {
      AnnouncementUpdateRequestDto dto = TEST_ANNOUNCEMENT_UPDATE_REQUEST_DTO_V1;

      given(announcementRepository.findById(any())).willThrow(EntityNotFoundException.class);

      Assertions.assertThrows(EntityNotFoundException.class,
          () -> announcementService.updateAnnouncement(dto));
    }

    @Test
    @DisplayName("공지사항 수정 테스트 실패, 쿠폰 존재 X")
    void 공지사항_수정_실패_테스트_쿠폰_존재X() {
      AnnouncementUpdateRequestDto dto = TEST_ANNOUNCEMENT_UPDATE_REQUEST_DTO_V1;

      given(announcementRepository.findById(any())).willReturn(
          Optional.ofNullable(TEST_ANNOUNCEMENT_V2));
      given(couponService.existCouponById(any())).willThrow(EntityNotFoundException.class);

      Assertions.assertThrows(EntityNotFoundException.class,
          () -> announcementService.updateAnnouncement(dto));
    }

  }

  @Nested
  @DisplayName("공지사항 삭제 테스트")
  class DeleteAnnouncement {

    @Test
    @DisplayName("공지사항 삭제 테스트 성공")
    void 공지사항_삭제_성공_테스트() {
      AnnouncementDeleteRequestDto dto = TEST_ANNOUNCEMENT_DELETE_REQUEST_DTO_V1;

      given(announcementRepository.findById(any())).willReturn(
          Optional.ofNullable(TEST_ANNOUNCEMENT_V1));

      announcementService.deleteAnnouncement(dto);

      verify(announcementRepository, atLeastOnce()).delete(any());
    }

    @Test
    @DisplayName("공지사항 삭제 테스트 실패")
    void 공지사항_삭제_실패_테스트() {
      AnnouncementDeleteRequestDto dto = TEST_ANNOUNCEMENT_DELETE_REQUEST_DTO_V1;

      given(announcementRepository.findById(any())).willThrow(EntityNotFoundException.class);

      Assertions.assertThrows(EntityNotFoundException.class,
          () -> announcementService.deleteAnnouncement(dto));
    }

  }

  @Nested
  @DisplayName("공지사항 조회 테스트")
  class ReadAnnouncement {

    @Test
    @DisplayName("공지사항 조회 성공 테스트")
    void 공지사항_조회_성공_테스트() {
      AnnouncementReadRequestDto dto = TEST_ANNOUNCEMENT_READ_REQUEST_DTO_V1;

      given(announcementRepository.findById(any())).willReturn(
          Optional.ofNullable(TEST_ANNOUNCEMENT_V1));

      AnnouncementReadResponseDto result = announcementService.getAnnouncement(dto);

      Assertions.assertEquals(result.getTitle(), TEST_ANNOUNCEMENT_TITLE_V1);
      Assertions.assertEquals(result.getContent(), TEST_ANNOUNCEMENT_TEXT_V1);
    }

    @Test
    @DisplayName("공지사항 리스트 조회 성공 테스트")
    void 공지사항_리스트_조회_성공_테스트() {
      given(announcementRepository.findAllByOrderByCreatedAtDesc(
          PageRequest.of(TEST_PAGE_NUMBER - 1, 10))).willReturn(TEST_ANNOUNCEMENT_LIST_V1);

      Page<AnnouncementsReadResponseDto> result = announcementService.getAnnouncementList(
          TEST_PAGE_NUMBER);

      Assertions.assertEquals(result.getContent().get(0).getAnnouncementId(),
          TEST_ANNOUNCEMENT_ID_V1);
      Assertions.assertEquals(result.getContent().get(0).getTitle(), TEST_ANNOUNCEMENT_TITLE_V1);
    }

    @Test
    @DisplayName("공지사항 조회 실패 테스트")
    void 공지사항_조회_실패_테스트() {
      AnnouncementReadRequestDto dto = TEST_ANNOUNCEMENT_READ_REQUEST_DTO_V1;

      given(announcementRepository.findById(any())).willThrow(EntityNotFoundException.class);

      Assertions.assertThrows(EntityNotFoundException.class,
          () -> announcementService.getAnnouncement(dto));
    }

  }

}
