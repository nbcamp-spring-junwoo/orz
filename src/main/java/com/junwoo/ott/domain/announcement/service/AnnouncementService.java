package com.junwoo.ott.domain.announcement.service;

import com.junwoo.ott.domain.announcement.dto.body.AnnouncementReadDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementCreateRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementDeleteRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementReadRequestDto;
import com.junwoo.ott.domain.announcement.dto.request.AnnouncementUpdateRequestDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementCreateResponseDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementListResponseDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementReadResponseDto;
import com.junwoo.ott.domain.announcement.dto.response.AnnouncementUpdateResponseDto;
import com.junwoo.ott.domain.announcement.entity.Announcement;
import com.junwoo.ott.domain.announcement.repository.AnnouncementRepository;
import com.junwoo.ott.domain.coupon.entity.Coupon;
import com.junwoo.ott.domain.coupon.service.CouponService;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional
@Service
public class AnnouncementService {

  private final CouponService couponService;

  private final AnnouncementRepository announcementRepository;

  @Transactional(readOnly = true)
  public AnnouncementReadResponseDto getAnnouncement(final AnnouncementReadRequestDto dto) {
    Announcement announcement = existAnnouncement(dto.getAnnouncementId());

    return new AnnouncementReadResponseDto(announcement);
  }

  @Transactional(readOnly = true)
  public AnnouncementListResponseDto getAnnouncementList() {
    List<Announcement> announcements = announcementRepository.findAll();

    List<AnnouncementReadDto> announcementReadDtoList = announcements.stream().map(
            announcement -> new AnnouncementReadDto(announcement.getAnnouncementId(),
                announcement.getTitle()))
        .toList();

    return new AnnouncementListResponseDto(announcementReadDtoList);
  }

  public AnnouncementCreateResponseDto createAnnouncement(
      final AnnouncementCreateRequestDto createRequestDto
  ) {
    Coupon coupon = couponService.existCouponById(createRequestDto.getCouponId());

    Announcement announcement = Announcement.builder()
        .title(createRequestDto.getTitle())
        .content(createRequestDto.getContent())
        .coupon(coupon)
        .build();

    Announcement savedAnnouncement = announcementRepository.save(announcement);
    return new AnnouncementCreateResponseDto(savedAnnouncement);
  }

  public AnnouncementUpdateResponseDto updateAnnouncement(
      final AnnouncementUpdateRequestDto updateRequestDto
  ) {
    Announcement announcement = existAnnouncement(updateRequestDto.getAnnouncementId());

    if (updateRequestDto.getCouponId() != null) {
      couponService.existCouponById(updateRequestDto.getCouponId());
    }

    Coupon coupon = couponService.existCouponById(updateRequestDto.getCouponId());

    announcement.updateAnnouncement(coupon, updateRequestDto.getTitle(),
        updateRequestDto.getContent());

    return new AnnouncementUpdateResponseDto(announcement);
  }

  public void deleteAnnouncement(final AnnouncementDeleteRequestDto dto) {
    Announcement announcement = existAnnouncement(dto.getAnnouncementId());

    announcementRepository.delete(announcement);
  }

  private Announcement existAnnouncement(final Long announcementId) {
    return announcementRepository.findById(announcementId)
        .orElseThrow(() -> new EntityNotFoundException("해당 공지사항이 존재하지 않습니다."));
  }

}
